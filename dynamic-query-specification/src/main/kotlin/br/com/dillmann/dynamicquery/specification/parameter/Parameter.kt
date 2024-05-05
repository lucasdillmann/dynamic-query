package br.com.dillmann.dynamicquery.specification.parameter

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root

/**
 * Dynamic Query's parameter encapsulation definition, which can be a reference (like an attribute name), a value
 * or even a operation
 */
interface Parameter {

    /**
     * Produces a JPA [Expression] from the current parameter with a generic type
     *
     * @param root Root of the JPA query (the main entity being queried)
     * @param query Current JPA query
     * @param builder JPA expression builder
     */
    fun asExpression(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Expression<out Any> =
        asExpression(root, query, builder, null)

    /**
     * Produces a JPA [Expression] from the current parameter with a specific type
     *
     * @param T Generic type of the expression
     * @param root Root of the JPA query (the main entity being queried)
     * @param query Current JPA query
     * @param builder JPA expression builder
     * @param targetType Target type of the expression. When informed, value will be parsed to such type before being
     * returned.
     */
    fun <T: Any> asExpression(
        root: Root<*>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder,
        targetType: Class<T>?,
    ): Expression<out T>
}
