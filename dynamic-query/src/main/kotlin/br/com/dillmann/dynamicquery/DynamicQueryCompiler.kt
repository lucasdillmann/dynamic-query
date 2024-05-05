package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameterType
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecificationFactory
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.parameter.ParameterFactory
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

            TreeNodeType.LOGICAL_OPERATOR ->
                error("Logical operators should be compiled indirectly by groups")
            TreeNodeType.OPERATION ->
                error("Operation operators should be compiled indirectly by predicates")
            TreeNodeType.PARAMETER_LITERAL ->
                error("Literal parameters should be compiled indirectly by predicates")
        }

    private fun compilePredicate(node: TreeNode): DynamicQuerySpecification {
        val type = PredicateType.forIdentifier(node.identifier!!)
        val parameters = compileParameters(node.children)
        return DynamicQuerySpecificationFactory.predicate(type, parameters)
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

    private fun compileParameters(nodes: List<TreeNode>?): List<Parameter> =
        nodes?.map(::compileParameter) ?: emptyList()

    private fun compileParameter(node: TreeNode): Parameter =
        if (node.type == TreeNodeType.OPERATION) compileOperationParameter(node)
        else compileBasicParameter(node)

    private fun compileOperationParameter(node: TreeNode): Parameter {
        val type = OperationParameterType.forIdentifier(node.identifier!!)
        val parameters = compileParameters(node.children)
        return ParameterFactory.operation(type, parameters)
    }

    private fun compileBasicParameter(node: TreeNode): Parameter {
        val (type, value) = node.parameter ?: error("Missing parameter metadata")
        return when (type) {
            TreeNodeParameterType.LITERAL -> ParameterFactory.literal(value)
            TreeNodeParameterType.ATTRIBUTE_NAME -> {
                require(value != null) { "When the parameter is a reference, its value must be a non-null String" }
                ParameterFactory.reference(value)
            }
        }
    }
}
