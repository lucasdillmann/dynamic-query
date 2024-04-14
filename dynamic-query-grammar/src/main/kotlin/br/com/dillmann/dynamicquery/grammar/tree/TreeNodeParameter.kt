package br.com.dillmann.dynamicquery.grammar.tree

/**
 * Metadata about a value, reference or chained call used as an argument to an operation call
 *
 * @property type Type of the parameter
 * @property value Value of the parameter
 */
data class TreeNodeParameter(
    val type: TreeNodeParameterType,
    val value: Any?,
)
