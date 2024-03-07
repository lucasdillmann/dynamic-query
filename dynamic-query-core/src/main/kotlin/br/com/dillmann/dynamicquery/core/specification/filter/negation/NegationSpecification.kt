package br.com.dillmann.dynamicquery.core.specification.filter.negation

import br.com.dillmann.dynamicquery.core.specification.Specification
import br.com.dillmann.dynamicquery.core.specification.filter.FilterSpecification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

/**
 * [FilterSpecification] specialization for negation expressions
 *
 * @param negatedExpression Expression to be inverted/negated
 */
class NegationSpecification(private val negatedExpression: Specification): FilterSpecification {

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
