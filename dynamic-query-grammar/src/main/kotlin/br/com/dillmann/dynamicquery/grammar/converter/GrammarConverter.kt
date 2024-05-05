package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarBaseListener
import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser.*
import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameter
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameterType
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
     * Notifies about the navigation entering an expression token. When called, start a new group node in the internal
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
     * Notifies about the navigation entering a predicate operation token. When called, start a new filter node in
     * the internal predicate tree.
     *
     * @param parserContext Details about the predicate token that is starting
     */
    override fun enterPredicate(parserContext: PredicateContext) {
        context.startNode(TreeNodeType.PREDICATE)
    }

    /**
     * Notifies about the navigation exiting a predicate operation token. When called, close the current node in the
     * internal predicate tree.
     *
     * @param parserContext Details about the predicate token that is ending
     */
    override fun exitPredicate(parserContext: PredicateContext) {
        context.endNode()
    }

    /**
     * Notifies about the navigation entering a operation token. When called, start a new filter node in
     * the internal predicate tree.
     *
     * @param parserContext Details about the predicate token that is starting
     */
    override fun enterOperation(parserContext: OperationContext) {
        context.startNode(TreeNodeType.OPERATION)
    }

    /**
     * Notifies about the navigation exiting a operation token. When called, close the current node in
     * the internal predicate tree.
     *
     * @param parserContext Details about the predicate token that is ending
     */
    override fun exitOperation(parserContext: OperationContext) {
        context.endNode()
    }

    /**
     * Notifies about the navigation exiting a string literal token. When called, includes the given value as a
     * parameter on the internal predicate tree.
     *
     * @param parserContext Details about the parameter literal token that is ending
     */
    override fun exitStringLiteral(parserContext: StringLiteralContext) {
        val parsedValue = parserContext.text.removeSurrounding("\"")
        addLiteralParameter(TreeNodeParameterType.LITERAL, parsedValue)
    }

    /**
     * Notifies about the navigation exiting a boolean literal token. When called, parses and includes the given value
     * as a parameter on the internal predicate tree.
     *
     * @param parserContext Details about the parameter literal token that is ending
     */
    override fun exitBooleanLiteral(parserContext: BooleanLiteralContext) {
        addLiteralParameter(TreeNodeParameterType.LITERAL, parserContext.text)
    }

    /**
     * Notifies about the navigation exiting a numeric literal token. When called, parses and includes the given value
     * as a parameter on the internal predicate tree.
     *
     * @param parserContext Details about the parameter literal token that is ending
     */
    override fun exitNumericLiteral(parserContext: NumericLiteralContext) {
        addLiteralParameter(TreeNodeParameterType.LITERAL, parserContext.text)
    }

    /**
     * Notifies about the navigation exiting a null literal token. When called, includes the given null value
     * as a parameter on the internal predicate tree.
     *
     * @param parserContext Details about the parameter literal token that is ending
     */
    override fun exitNullLiteral(parserContext: NullLiteralContext) {
        addLiteralParameter(TreeNodeParameterType.LITERAL, null)
    }

    /**
     * Notifies about the navigation exiting a attribute reference by its name. When called, includes the given name
     * as a parameter on the internal predicate tree.
     *
     * @param parserContext Details about the attribute reference token that is ending
     */
    override fun exitAttributeName(parserContext: AttributeNameContext) {
        addLiteralParameter(TreeNodeParameterType.ATTRIBUTE_NAME, parserContext.text)
    }

    /**
     * Notifies about the navigation exiting a predicate type token. When called, stores the given type as the
     * predicate operation to be executed on the current internal predicate tree's node.
     *
     * @param parserContext Details about the operation token that is ending
     */
    override fun exitPredicateType(parserContext: PredicateTypeContext) {
        context.currentNode.identifier = parserContext.text
    }

    /**
     * Notifies about the navigation exiting an operation type token. When called, stores the given type as the
     * operation to be executed on the current internal predicate tree's node.
     *
     * @param parserContext Details about the operation type token that is ending
     */
    override fun exitOperationType(parserContext: OperationTypeContext) {
        context.currentNode.identifier = parserContext.text
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

    private fun addLiteralParameter(type: TreeNodeParameterType, value: String?) {
        context.startNode(TreeNodeType.PARAMETER_LITERAL)
        context.currentNode.parameter = TreeNodeParameter(type, value)
        context.endNode()
    }
}
