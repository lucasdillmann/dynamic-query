package br.com.dillmann.dynamicquery.core.grammar.exception

import br.com.dillmann.dynamicquery.core.DynamicQueryException

/**
 * [DynamicQueryException] specialization for grammar-related errors
 *
 * @param message Description of what happened
 * @param cause Exception that lead to the error
 */
open class GrammarException(message: String, cause: Exception? = null): DynamicQueryException(message, cause)
