package br.com.dillmann.dynamicquery.core.specification.exception

import javax.persistence.criteria.Expression

/**
 * [SpecificationException] specialization for the error scenarios where an operation was requested on an attribute
 * that holds an unsupported value type
 *
 * @param expression Path of the attribute
 * @param value Received value
 * @param operationName Name of the requested operation that doesn't support the provided type of value
 */
class UnsupportedValueTypeException(val expression: Expression<out Any>, val value: Any, val operationName: String):
    SpecificationException("Unsupported value of type [${value.javaClass.name}] provided in the operation [$operationName]")
