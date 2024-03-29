package br.com.dillmann.dynamicquery.springboot.datajpa

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification

/**
 * [Specification] adapter for the Dynamic Query's [Specification]
 *
 * @param T Generic type of the specification's target entity
 * @param delegate Dynamic Query's specification to be adapted to the [Specification]
 */
class DynamicQuerySpecificationAdapter<T>(private val delegate: DynamicQuerySpecification): Specification<T> {

    /**
     * Produces a JPA-compliant predicate using the given criteria. Every call will be delegated to the adapted
     * Dynamic Query's specification.
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<T>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate =
        delegate.toPredicate(root, query, builder)

}
