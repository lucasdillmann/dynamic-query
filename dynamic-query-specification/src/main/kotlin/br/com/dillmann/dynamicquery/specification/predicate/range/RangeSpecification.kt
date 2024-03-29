package br.com.dillmann.dynamicquery.specification.predicate.range

import br.com.dillmann.dynamicquery.specification.path.PathResolver
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import jakarta.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for ranges filter expressions
 *
 * @property attributeName Name of the attribute
 * @property rangeStartValue Left (start) value of the range
 * @property rangeEndValue Left (end) value of the range
 * @param negateResults Defines if the predicate should be negated or not
 */
abstract class RangeSpecification(
    val attributeName: String,
    val rangeStartValue: String,
    val rangeEndValue: String,
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
        val path = PathResolver.resolve(attributeName, root) as Path<Comparable<Any>>
        val parsedRangeStart = ValueParsers.parse(rangeStartValue, path.javaType)
        val parsedRangeEnd = ValueParsers.parse(rangeEndValue, path.javaType)
        val predicate = builder.between(path, parsedRangeStart, parsedRangeEnd)
        return if (negateResults) predicate.not() else predicate
    }
}
