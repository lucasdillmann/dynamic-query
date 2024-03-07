package br.com.dillmann.dynamicquery.core.specification.filter.unary

import br.com.dillmann.dynamicquery.core.specification.filter.FilterSpecification
import br.com.dillmann.dynamicquery.core.specification.path.PathResolver
import javax.persistence.criteria.*

/**
 * [FilterSpecification] specialization for unary filter expressions
 *
 * @param attributeName Name of the attribute
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
abstract class UnarySpecification(
    private val attributeName: String,
    private val builderFunction: CriteriaBuilder.(Expression<out Any>) -> Predicate,
): FilterSpecification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate {
        val path = PathResolver.resolve(attributeName, root)
        return builder.builderFunction(path)
    }
}
