package br.com.dillmann.dynamicquery.core

/**
 * Base exception for all the library's thrown errors
 *
 * @param message Description of what happened
 * @param cause Exception that lead to the error
 */
open class DynamicQueryException(message: String, cause: Exception? = null): RuntimeException(message, cause)
