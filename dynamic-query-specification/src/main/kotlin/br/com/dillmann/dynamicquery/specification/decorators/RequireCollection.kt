package br.com.dillmann.dynamicquery.specification.decorators

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate

/**
 * Builds and returns a proxy for the [CriteriaBuilder] function reference that casts the expression as a
 * Collection-based one.
 *
 * @param delegate Delegate function reference to be called if the input value is a String
 */
internal fun requireCollection(
    delegate: CriteriaBuilder.(Expression<Collection<Any>>) -> Predicate
): CriteriaBuilder.(Expression<out Any>) -> Predicate = proxy@{ expression ->
    @Suppress("UNCHECKED_CAST")
    return@proxy delegate(expression as Expression<Collection<Any>>)
}
