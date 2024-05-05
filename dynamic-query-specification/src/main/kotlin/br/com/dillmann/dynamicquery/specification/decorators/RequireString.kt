package br.com.dillmann.dynamicquery.specification.decorators

import br.com.dillmann.dynamicquery.specification.exception.UnsupportedValueTypeException
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate

/**
 * Builds and returns a proxy for the [CriteriaBuilder] function reference that checks the value type and validated
 * that it's a [String], throwing an [UnsupportedValueTypeException] otherwise.
 *
 * @param operationName Name of the target operation
 * @param delegate Delegate function reference to be called if the input value is a String
 */
internal fun requireString(
    operationName: String,
    delegate: CriteriaBuilder.(Expression<String>, Expression<String>) -> Predicate,
): CriteriaBuilder.(Expression<Comparable<Any>>, Expression<out Comparable<Any>>) -> Predicate = proxy@{ path, value ->
    if (!String::class.java.isAssignableFrom(value.javaType))
        throw UnsupportedValueTypeException(path, value, operationName)

    @Suppress("UNCHECKED_CAST")
    delegate(path as Expression<String>, value as Expression<String>)
}
