package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.path.PathResolver
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import jakarta.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for binary filter expressions
 *
 * @property attributeName Full path of the attribute
 * @property value Value to be compared to
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
abstract class BinarySpecification(
    val attributeName: String,
    val value: String,
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
