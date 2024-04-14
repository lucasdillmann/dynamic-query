package br.com.dillmann.dynamicquery.grammar.tree

/**
 * Supported types of operation call arguments
 */
enum class TreeNodeParameterType {
    /**
     * Boolean literal values, such as true or false
     */
    BOOLEAN_LITERAL,

    /**
     * Numeric literal values, such as 1, 3.45 and alike
     */
    NUMERIC_LITERAL,

    /**
     * String literal values
     */
    STRING_LITERAL,

    /**
     * Null literal value
     */
    NULL_LITERAL,

    /**
     * Reference to an attribute by its name
     */
    ATTRIBUTE_NAME,

    /**
     * An operation call that produces an output
     */
    OPERATION,
}
