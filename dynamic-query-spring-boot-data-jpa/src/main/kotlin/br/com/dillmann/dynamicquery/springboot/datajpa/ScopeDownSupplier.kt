package br.com.dillmann.dynamicquery.springboot.datajpa

import org.springframework.data.jpa.domain.Specification

/**
 * Type alias of a function that receives a [Specification] and produces a [Specification]
 *
 * @param T Generic type of the entity
 */
typealias ScopeDownSupplier<T> = (Specification<T>) -> Specification<T>
