package br.com.dillmann.dynamicquery.springbootdatajpa

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import org.springframework.data.jpa.domain.Specification

/**
 * Extension function for the [DynamicQuerySpecification] able to convert the instance to a Spring Data [Specification]
 *
 * @param T Generic type of the specification's target entity
 */
fun <T> DynamicQuerySpecification.toSpringSpecification(): Specification<T> =
    DynamicQuerySpecificationAdapter(this)

/**
 * Extension function for the [DynamicQuerySpecification] able to convert the instance to a Spring Data [Specification]
 * and apply additional predicates
 *
 * @param T Generic type of the specification's target entity
 * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
 * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
 * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
 */
fun <T> DynamicQuerySpecification.toSpringSpecification(scopeDownSupplier: ScopeDownSupplier<T>): Specification<T> =
    scopeDownSupplier(toSpringSpecification())
