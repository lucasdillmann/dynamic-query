package br.com.dillmann.dynamicquery.core

import br.com.dillmann.dynamicquery.core.specification.Specification

/**
 * Dynamic Query parse result
 *
 * @param expression Original expression
 * @param specification Produced specification from the input expression
 */
data class DynamicQuery(
    val expression: String,
    val specification: Specification,
)
