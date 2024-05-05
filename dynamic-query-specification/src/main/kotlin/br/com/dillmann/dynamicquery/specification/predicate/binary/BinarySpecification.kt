package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import jakarta.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for binary filter expressions
 *
 * @property target Target of the operation (such as the attribute name)
 * @property value Value to be compared to
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
internal abstract class BinarySpecification(
    val target: Parameter,
    val value: Parameter,
    private val builderFunction:
        CriteriaBuilder.(Expression<Comparable<Any>>, Expression<out Comparable<Any>>) -> Predicate,
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
        val parsedValue = value.asExpression(root, query, builder, path.javaType)
        return builder.builderFunction(path, parsedValue)
    }
}
