package br.com.dillmann.dynamicquery.specification.decorators

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate

/**
 * Builds and returns a proxy for the [CriteriaBuilder] function reference that perform a case-insensitive conversion
 * and configuration before calling the delegate function.
 *
 * @param operationName Name of the target operation
 * @param delegate Delegate function reference to be called if the input value is a String
 */
internal fun caseInsensitive(
    operationName: String,
    delegate: CriteriaBuilder.(Expression<String>, Expression<String>) -> Predicate,
) = requireString(operationName) { path, value ->
    delegate(lower(path), lower(value))
}
