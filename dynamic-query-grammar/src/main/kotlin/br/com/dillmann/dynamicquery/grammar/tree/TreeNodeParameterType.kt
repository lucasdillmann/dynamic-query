package br.com.dillmann.dynamicquery.grammar.tree

/**
 * Supported types of operation call arguments
 */
enum class TreeNodeParameterType {
    /**
     * Literal values, such as true or false, a String, number and alike
     */
    LITERAL,

    /**
     * Reference to an attribute by its name
     */
    ATTRIBUTE_NAME,
}
