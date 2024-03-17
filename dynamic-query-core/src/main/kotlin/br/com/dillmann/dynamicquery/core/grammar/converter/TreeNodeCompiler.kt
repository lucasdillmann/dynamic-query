package br.com.dillmann.dynamicquery.core.grammar.converter

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecificationFactory
import br.com.dillmann.dynamicquery.core.specification.group.GroupSpecification
import br.com.dillmann.dynamicquery.core.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.core.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.core.specification.predicate.PredicateType

/**
 * [TreeNode] to [DynamicQuerySpecification] compiler
 *
 * @param rootNode Root node of the tree to be compiled
 */
class TreeNodeCompiler(private val rootNode: TreeNode) {

    /**
     * Starts the compilation from the [rootNode], returning the produced [DynamicQuerySpecification]
     */
    fun compile(): DynamicQuerySpecification =
        compile(rootNode)

    private fun compile(node: TreeNode): DynamicQuerySpecification =
        when (node.type) {
            TreeNodeType.GROUP -> compileChildren(node)
            TreeNodeType.PREDICATE -> compilePredicate(node)
            TreeNodeType.NEGATION -> compileNegation(node)
            TreeNodeType.LOGICAL_OPERATOR -> error("Logical operators should be compiled indirectly by groups")
        }

    private fun compilePredicate(node: TreeNode): PredicateSpecification {
        val predicateType = PredicateType.forIdentifier(node.operation!!)
        return DynamicQuerySpecificationFactory.predicate(
            predicateType,
            node.attributeName!!,
            node.parameters ?: emptyList(),
        )
    }

    private fun compileNegation(node: TreeNode): PredicateSpecification {
        val children = compileChildren(node)
        return DynamicQuerySpecificationFactory.negate(children)
    }

    private fun compileLogicalOperator(
        node: TreeNode,
        leftExpression: DynamicQuerySpecification,
        rightExpression: DynamicQuerySpecification
    ): GroupSpecification {
        val logicalOperator = LogicalOperatorType.forIdentifier(node.logicalOperator!!)
        return DynamicQuerySpecificationFactory.group(logicalOperator, leftExpression, rightExpression)
    }

    private fun compileChildren(parent: TreeNode): DynamicQuerySpecification {
        var leftSpecification: DynamicQuerySpecification? = null
        var logicalOperatorNode: TreeNode? = null

        parent.children.forEach { child ->
            if (child.type == TreeNodeType.LOGICAL_OPERATOR) {
                logicalOperatorNode = child
            } else {
                val currentPredicate = compile(child)
                leftSpecification =
                    if (leftSpecification == null) currentPredicate
                    else compileLogicalOperator(logicalOperatorNode!!, leftSpecification!!, currentPredicate)
            }
        }

        return leftSpecification!!
    }
}
