package br.com.dillmann.dynamicquery.core.specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

/**
 * Base representation of the Specification pattern (from the Domain Driven Design) for a JPA-based query
 */
interface Specification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate
}
