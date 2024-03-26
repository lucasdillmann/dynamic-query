package br.com.dillmann.dynamicquery.springboot.datajpa

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

/**
 * Spring Data repository definition for the Dynamic Query's specifications
 *
 * @param T Generic type of the entity
 */
@NoRepositoryBean
interface DynamicQueryRepository<T> : JpaSpecificationExecutor<T> {

    /**
     * Queries and returns a single result using provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     */
    fun findOne(specification: DynamicQuerySpecification?): Optional<T> =
        findOne(specification.toSpringSpecification())

    /**
     * Queries and returns a single result using provided [Specification] and a scope-down
     * supplier, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun findOne(specification: DynamicQuerySpecification?, scopeDownSupplier: ScopeDownSupplier<T>): Optional<T> =
        findOne(specification.toSpringSpecification(scopeDownSupplier))

    /**
     * Queries and returns a non-paginated list of results using provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     */
    fun findAll(specification: DynamicQuerySpecification?): List<T> =
        findAll(specification.toSpringSpecification())

    /**
     * Queries and returns a non-paginated list of results using provided [Specification] and a scope-down
     * supplier, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun findAll(specification: DynamicQuerySpecification?, scopeDownSupplier: ScopeDownSupplier<T>): List<T> =
        findAll(specification.toSpringSpecification(scopeDownSupplier))

    /**
     * Queries and returns a paginated list of results using provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param page Page settings (number, size and alike)
     */
    fun findAll(specification: DynamicQuerySpecification?, page: Pageable): Page<T> =
        findAll(specification.toSpringSpecification(), page)

    /**
     * Queries and returns a paginated list of results using provided [Specification] and a scope-down
     * supplier, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param page Page settings (number, size and alike)
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun findAll(
        specification: DynamicQuerySpecification?,
        page: Pageable,
        scopeDownSupplier: ScopeDownSupplier<T>,
    ): Page<T> =
        findAll(specification.toSpringSpecification(scopeDownSupplier), page)

    /**
     * Queries and returns a sorted, non-paginated list of results using provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param sorting Sorting settings (attributes, order and alike)
     */
    fun findAll(specification: DynamicQuerySpecification?, sorting: Sort): List<T> =
        findAll(specification.toSpringSpecification(), sorting)

    /**
     * Queries and returns a sorted, non-paginated list of results using provided [Specification] and a
     * scope-down supplier, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param sorting Sorting settings (attributes, order and alike)
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun findAll(
        specification: DynamicQuerySpecification?,
        sorting: Sort,
        scopeDownSupplier: ScopeDownSupplier<T>,
    ): List<T> =
        findAll(specification.toSpringSpecification(scopeDownSupplier), sorting)

    /**
     * Queries and returns the amount of records that matches the provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     */
    fun count(specification: DynamicQuerySpecification?): Long =
        count(specification.toSpringSpecification())

    /**
     * Queries and returns the amount of records that matches the provided [Specification] and a
     * scope-down supplied conditions, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun count(specification: DynamicQuerySpecification?, scopeDownSupplier: ScopeDownSupplier<T>): Long =
        count(specification.toSpringSpecification(scopeDownSupplier))

    /**
     * Queries and returns if any record matches the provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     */
    fun exists(specification: DynamicQuerySpecification?): Boolean =
        exists(specification.toSpringSpecification())

    /**
     * Queries and returns if any record matches the provided [Specification] and a
     * scope-down supplied conditions, which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun exists(specification: DynamicQuerySpecification?, scopeDownSupplier: ScopeDownSupplier<T>): Boolean =
        exists(specification.toSpringSpecification(scopeDownSupplier))

    /**
     * Deletes any records that matches the provided [Specification]
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     */
    fun delete(specification: DynamicQuerySpecification?): Long =
        delete(specification.toSpringSpecification())

    /**
     * Deletes any records that matches the provided [Specification] and a scope-down supplied conditions,
     * which allows a set of fixed conditions to be placed on top of the dynamic ones
     *
     * @param specification Dynamic Query's specification with the query predicates. If null is supplied, an empty
     * predicate will be used with no query restrictions.
     * @param scopeDownSupplier Specification transformer able to customize the generated [Specification] from the
     * Dynamic Query's predicates. Please note that the supplier provided [Specification] will be used as-is, and
     * is your responsibility to link (with an and/or expression) your query conditions with the Dynamic Query's ones.
     */
    fun delete(specification: DynamicQuerySpecification?, scopeDownSupplier: ScopeDownSupplier<T>): Long =
        delete(specification.toSpringSpecification(scopeDownSupplier))
}
