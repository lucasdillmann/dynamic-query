package br.com.dillmann.dynamicquery.core.specification.predicate.negation

import br.com.dillmann.dynamicquery.core.specification.Specification
import br.com.dillmann.dynamicquery.core.specification.predicate.PredicateSpecification
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

/**
 * [PredicateSpecification] specialization for negation expressions
 *
 * @property negatedExpression Expression to be inverted/negated
 */
class NegationSpecification(val negatedExpression: Specification): PredicateSpecification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate =
        negatedExpression.toPredicate(root, query, builder).not()
}
