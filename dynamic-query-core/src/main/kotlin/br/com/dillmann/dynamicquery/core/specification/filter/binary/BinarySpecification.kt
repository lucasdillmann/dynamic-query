package br.com.dillmann.dynamicquery.core.specification.filter.binary

import br.com.dillmann.dynamicquery.core.specification.filter.PredicateSpecification
import br.com.dillmann.dynamicquery.core.specification.path.PathResolver
import br.com.dillmann.dynamicquery.core.valueparser.ValueParsers
import javax.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for binary filter expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
abstract class BinarySpecification(
    private val attributeName: String,
    private val value: String,
    private val builderFunction: CriteriaBuilder.(Path<Comparable<Any>>, Comparable<Any>) -> Predicate,
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
        val parsedValue = ValueParsers.parse(value, path.javaType)
        return builder.builderFunction(path, parsedValue)
    }
}
