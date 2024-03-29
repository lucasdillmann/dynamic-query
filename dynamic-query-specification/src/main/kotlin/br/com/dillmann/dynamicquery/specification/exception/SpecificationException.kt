package br.com.dillmann.dynamicquery.specification.exception

/**
 * [RuntimeException] specialization for the error scenarios of a JPA specification
 *
 * @param message Details about what happened
 * @param cause Exception that lead to the error
 */
open class SpecificationException(message: String, cause: Exception? = null):
    RuntimeException(message, cause)
