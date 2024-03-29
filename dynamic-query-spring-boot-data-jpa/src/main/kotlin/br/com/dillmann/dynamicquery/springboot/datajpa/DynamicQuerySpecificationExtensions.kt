package br.com.dillmann.dynamicquery.springboot.datajpa

import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import org.springframework.data.jpa.domain.Specification

/**
 * Extension function for the [DynamicQuerySpecification] able to convert the instance to a Spring Data [Specification].
 * If called on a null receiver/context, an empty [Specification] (with no query restrictions) will be returned.
 *
 * @param T Generic type of the specification's target entity
 */
fun <T> DynamicQuerySpecification?.toSpringSpecification(): Specification<T> =
    if (this != null) DynamicQuerySpecificationAdapter(this)
    else Specification.where { _, query, _ -> query.restriction }

/**
 * Extension function for the [DynamicQuerySpecification] able to convert the instance to a Spring Data [Specification]
 * and apply additional predicates. If called on a null receiver/context, an empty [Specification] (with no query
 * restrictions) will be used as the argument for the [scopeDownSupplier].
 *
 * @param T Generic type of the specification's target entity
 * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
 * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
 * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
 */
fun <T> DynamicQuerySpecification?.toSpringSpecification(scopeDownSupplier: ScopeDownSupplier<T>): Specification<T> =
    scopeDownSupplier(toSpringSpecification())
