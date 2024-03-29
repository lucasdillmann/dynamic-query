package br.com.dillmann.dynamicquery.specification.group

import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

/**
 * [DynamicQuerySpecification] specialization for logical operators
 *
 * @property leftExpression Left side expression of the operator
 * @property rightExpression Right side expression of the operator
 * @param builderFunction Criteria Builder's function for the specific type of binary operation
 */
abstract class GroupSpecification(
    val leftExpression: DynamicQuerySpecification,
    val rightExpression: DynamicQuerySpecification,
    private val builderFunction: CriteriaBuilder.(Predicate, Predicate) -> Predicate,
): DynamicQuerySpecification {

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
