package br.com.dillmann.dynamicquery.specification.predicate.range

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import jakarta.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for ranges filter expressions
 *
 * @property target Target of the operation (such as the attribute name)
 * @property rangeStartValue Left (start) value of the range
 * @property rangeEndValue Left (end) value of the range
 * @param negateResults Defines if the predicate should be negated or not
 */
internal abstract class RangeSpecification(
    val target: Parameter,
    val rangeStartValue: Parameter,
    val rangeEndValue: Parameter,
    private val negateResults: Boolean,
): PredicateSpecification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate {
        @Suppress("UNCHECKED_CAST")
        val path = target.asExpression(root, query, builder) as Expression<Comparable<Any>>
        val rangeStart = rangeStartValue.asExpression(root, query, builder, path.javaType)
        val rangeEnd = rangeEndValue.asExpression(root, query, builder, path.javaType)
        val predicate = builder.between(path, rangeStart, rangeEnd)
        return if (negateResults) predicate.not() else predicate
    }
}
