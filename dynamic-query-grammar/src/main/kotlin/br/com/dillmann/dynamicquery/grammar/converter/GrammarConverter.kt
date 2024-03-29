package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarBaseListener
import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser.*
import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType

/**
 * Dynamic Query DSL parsed tree converter to JPA-based Specification
 *
 * This class works as an event listener for the Dynamic Query DSL parsed tree navigation, able to detect important
 * events that identify when, for example, a token was started or ended and store such information in an internal tree
 * that can later-on be retrieved using the [rootNode] method.
 */
class GrammarConverter : DynamicQueryGrammarBaseListener() {

    private val context = GrammarConversionContext()

    /**
     * Returns the conversion result as a root [TreeNode]
     */
    fun rootNode() =
        context.root

    /**
     * Notifies about the navigation entering an expression token. When called, starts a new group node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the expression token that is starting
     */
    override fun enterExpression(parserContext: ExpressionContext) {
        context.startNode(TreeNodeType.GROUP)
    }

    /**
     * Notifies about the navigation exiting an expression token. When called, closes the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the expression token that is ending
     */
    override fun exitExpression(parserContext: ExpressionContext) {
        context.endNode()
    }

    /**
     * Notifies about the navigation entering a predicate token. When called, starts a new filter node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the predicate token that is starting
     */
    override fun enterPredicate(parserContext: PredicateContext) {
        context.startNode(TreeNodeType.PREDICATE)
    }

    /**
     * Notifies about the navigation exiting a predicate token. When called, closes the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the predicate token that is ending
     */
    override fun exitPredicate(parserContext: PredicateContext) {
        context.endNode()
    }

    /**
     * Notifies about the navigation exiting an attribute name token. When called, stores the given name as the name
     * of the target attribute on the current internal predicate tree's node.
     *
     * @param parserContext Details about the attribute name token that is ending
     */
    override fun exitAttributeName(parserContext: AttributeNameContext) {
        context.currentNode.attributeName = parserContext.text
    }

    /**
     * Notifies about the navigation exiting an operation token. When called, stores the given operation as the
     * operation to be executed on the current internal predicate tree's node.
     *
     * @param parserContext Details about the operation token that is ending
     */
    override fun exitOperation(parserContext: OperationContext) {
        context.currentNode.operation = parserContext.text
    }

    /**
     * Notifies about the navigation entering a parameter token. When called, prepares the current internal predicate
     * tree's token to be able to receive such parameters.
     *
     * @param parserContext Details about the parameter token that is starting
     */
    override fun enterParameters(parserContext: ParametersContext) {
        context.currentNode.parameters = emptyList()
    }

    /**
     * Notifies about the navigation exiting a string parameter token. When called, stores such parameter value
     * in the list of parameters (previously initialized by [enterParameters]) of the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the string parameter token that is ending
     */
    override fun exitParameterStringValue(parserContext: ParameterStringValueContext) {
        context.currentNode.parameters =
            context.currentNode.parameters!! + parserContext.text.removeSurrounding("\"")
    }

    /**
     * Notifies about the navigation exiting a numeric parameter token. When called, stores such parameter value
     * in the list of parameters (previously initialized by [enterParameters]) of the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the numeric parameter token that is ending
     */
    override fun exitParameterNumericValue(parserContext: ParameterNumericValueContext) {
        context.currentNode.parameters =
            context.currentNode.parameters!! + parserContext.text
    }

    /**
     * Notifies about the navigation exiting a boolean parameter token. When called, stores such parameter value
     * in the list of parameters (previously initialized by [enterParameters]) of the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the boolean parameter token that is ending
     */
    override fun exitParameterBooleanLiteral(parserContext: ParameterBooleanLiteralContext) {
        context.currentNode.parameters =
            context.currentNode.parameters!! + parserContext.text
    }

    /**
     * Notifies about the navigation entering a negation token. When called, starts a new negation node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the negation token that is starting
     */
    override fun enterNegation(parserContext: NegationContext) {
        context.startNode(TreeNodeType.NEGATION)
    }

    /**
     * Notifies about the navigation exiting a negation token. When called, closes the current node in the internal
     * predicate tree.
     *
     * @param parserContext Details about the negation token that is ending
     */
    override fun exitNegation(parserContext: NegationContext) {
        context.endNode()
    }

    /**
     * Notifies about the navigation exiting a logical operator token. When called, inserts a new node in the internal
     * predicate tree for the given operator.
     *
     * @param parserContext Details about the logical operator token that is ending
     */
    override fun exitLogicalOperator(parserContext: LogicalOperatorContext) {
        context.startNode(TreeNodeType.LOGICAL_OPERATOR)
        context.currentNode.logicalOperator = parserContext.text
        context.endNode()
    }

}
