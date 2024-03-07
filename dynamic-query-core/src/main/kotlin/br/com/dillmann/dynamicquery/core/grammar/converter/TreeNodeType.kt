package br.com.dillmann.dynamicquery.core.grammar.converter

/**
 * Conversion tree's node types enumeration
 *
 * @param allowsChildren Defines if the type of the node can have child nodes
 */
enum class TreeNodeType(val allowsChildren: Boolean) {

    /**
     * Tree root node
     */
    ROOT(true),

    /**
     * Group of expressions
     */
    GROUP(true),

    /**
     * Filter expression
     */
    FILTER(false),

    /**
     * Logical operator expression
     */
    LOGICAL_OPERATOR(false),

    /**
     * Negation of a set of child expressions
     */
    NEGATION(true),
}
