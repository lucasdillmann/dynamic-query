package br.com.dillmann.dynamicquery.grammar.tree

/**
 * Conversion tree's node types enumeration
 *
 * @property allowsChildren Defines if the type of the node can have child nodes
 */
enum class TreeNodeType(val allowsChildren: Boolean) {

    /**
     * Group of expressions
     */
    GROUP(true),

    /**
     * Predicate expression
     */
    PREDICATE(true),

    /**
     * Operation expression
     */
    OPERATION(true),

    /**
     * Logical operator expression
     */
    LOGICAL_OPERATOR(false),

    /**
     * Negation of a set of child expressions
     */
    NEGATION(true),

    /**
     * A parameter (string literal, boolean, expression, etc) of any given operation
     */
    PARAMETER_LITERAL(false),
}
