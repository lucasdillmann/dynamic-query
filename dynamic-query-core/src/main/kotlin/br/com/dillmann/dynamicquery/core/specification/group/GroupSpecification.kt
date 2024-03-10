package br.com.dillmann.dynamicquery.core.specification.group

import br.com.dillmann.dynamicquery.core.specification.Specification
import javax.persistence.criteria.*

/**
 * [Specification] specialization for logical operators
 *
 * @param leftExpression Left side expression of the operator
 * @param rightExpression Right side expression of the operator
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
abstract class GroupSpecification(
    val leftExpression: Specification,
    val rightExpression: Specification,
    private val builderFunction: CriteriaBuilder.(Predicate, Predicate) -> Predicate,
): Specification {

    /**
     * Produces a JPA-compliant predicate for a logic operation using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate {
        val leftPredicate = leftExpression.toPredicate(root, query, builder)
        val rightPredicate = rightExpression.toPredicate(root, query, builder)
        return builder.builderFunction(leftPredicate, rightPredicate)
    }
}
