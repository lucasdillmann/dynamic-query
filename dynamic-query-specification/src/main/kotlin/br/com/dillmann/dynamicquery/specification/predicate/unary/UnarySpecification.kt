package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import jakarta.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for unary filter expressions
 *
 * @property target Target of the operation (such as the attribute name)
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
internal abstract class UnarySpecification(
    val target: Parameter,
    private val builderFunction: CriteriaBuilder.(Expression<out Any>) -> Predicate,
): PredicateSpecification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate {
        val expression = target.asExpression(root, query, builder)
        return builder.builderFunction(expression)
    }
}
