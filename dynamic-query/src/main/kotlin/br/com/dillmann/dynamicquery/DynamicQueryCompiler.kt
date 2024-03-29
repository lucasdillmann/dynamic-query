package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecificationFactory
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType

/**
 * [TreeNode] to [DynamicQuerySpecification] compiler
 */
object DynamicQueryCompiler {

    /**
     * Compiles the provided [TreeNode], returning the produced [DynamicQuerySpecification]
     *
     * @param node Root node of the tree to be compiled
     */
    fun compile(node: TreeNode): DynamicQuerySpecification =
        when (node.type) {
            TreeNodeType.GROUP -> compileChildren(node)
            TreeNodeType.PREDICATE -> compilePredicate(node)
            TreeNodeType.NEGATION -> compileNegation(node)
            TreeNodeType.LOGICAL_OPERATOR -> error("Logical operators should be compiled indirectly by groups")
        }

    private fun compilePredicate(node: TreeNode): DynamicQuerySpecification {
        val predicateType = PredicateType.forIdentifier(node.operation!!)
        return DynamicQuerySpecificationFactory.predicate(
            predicateType,
            node.attributeName!!,
            node.parameters ?: emptyList(),
        )
    }

    private fun compileNegation(node: TreeNode): DynamicQuerySpecification {
        val children = compileChildren(node)
        return DynamicQuerySpecificationFactory.negate(children)
    }

    private fun compileLogicalOperator(
        node: TreeNode,
        leftExpression: DynamicQuerySpecification,
        rightExpression: DynamicQuerySpecification
    ): DynamicQuerySpecification {
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
