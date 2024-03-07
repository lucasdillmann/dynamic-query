package br.com.dillmann.dynamicquery.core.specification.decorators

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

/**
 * Builds and returns a proxy for the [CriteriaBuilder] function reference that perform a case-insensitive conversion
 * and configuration before calling the delegate function.
 *
 * @param operationName Name of the target operation
 * @param delegate Delegate function reference to be called if the input value is a String
 */
internal fun caseInsensitive(
    operationName: String,
    delegate: CriteriaBuilder.(Expression<String>, String) -> Predicate,
) = requireString(operationName) { path, value ->
    val lowerException = lower(path)
    return@requireString delegate(lowerException, value.lowercase())
}
