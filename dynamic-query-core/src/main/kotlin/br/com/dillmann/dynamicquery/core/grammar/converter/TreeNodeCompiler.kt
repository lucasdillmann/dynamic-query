package br.com.dillmann.dynamicquery.core.grammar.converter

import br.com.dillmann.dynamicquery.core.specification.Specification
import br.com.dillmann.dynamicquery.core.specification.SpecificationFactory
import br.com.dillmann.dynamicquery.core.specification.filter.PredicateSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.PredicateType
import br.com.dillmann.dynamicquery.core.specification.group.GroupSpecification
import br.com.dillmann.dynamicquery.core.specification.group.LogicalOperatorType

/**
 * [TreeNode] to [Specification] compiler
 *
 * @param rootNode Root node of the tree to be compiled
 */
class TreeNodeCompiler(private val rootNode: TreeNode) {

    /**
     * Starts the compilation from the [rootNode], returning the produced [Specification]
     */
    fun compile(): Specification =
        compile(rootNode)

    private fun compile(node: TreeNode): Specification =
        when (node.type) {
            TreeNodeType.GROUP -> compileChildren(node)
            TreeNodeType.PREDICATE -> compilePredicate(node)
            TreeNodeType.NEGATION -> compileNegation(node)
            TreeNodeType.LOGICAL_OPERATOR -> error("Logical operators should be compiled indirectly by groups")
        }

    private fun compilePredicate(node: TreeNode): PredicateSpecification {
        val predicateType = PredicateType.forIdentifier(node.operation!!)
        return SpecificationFactory.predicate(predicateType, node.attributeName!!, node.parameters ?: emptyList())
    }

    private fun compileNegation(node: TreeNode): PredicateSpecification {
        val children = compileChildren(node)
        return SpecificationFactory.negate(children)
    }

    private fun compileLogicalOperator(
        node: TreeNode,
        leftExpression: Specification,
        rightExpression: Specification
    ): GroupSpecification {
        val logicalOperator = LogicalOperatorType.forIdentifier(node.logicalOperator!!)
        return SpecificationFactory.group(logicalOperator, leftExpression, rightExpression)
    }

    private fun compileChildren(parent: TreeNode): Specification {
        var leftSpecification: Specification? = null
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
