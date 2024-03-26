package br.com.dillmann.dynamicquery.springboot.datajpa

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.jpa.domain.Specification
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * [DynamicQuerySpecification] extensions unit tests
 */
class DynamicQuerySpecificationExtensionsUnitTests {

    private val specification = mockk<DynamicQuerySpecification>()
    private val scopeDownSupplier = mockk<ScopeDownSupplier<Any>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val emptyPredicate = mockk<Predicate>()
    private val supplierResponse = mockk<Specification<Any>>()

    @BeforeEach
    fun setUp() {
        every { query.restriction } returns emptyPredicate
        every { scopeDownSupplier(any()) } returns supplierResponse
    }

    @Test
    fun `toSpringSpecification without a scope down supplier should return the receiver encapsulated in a DynamicQuerySpecificationAdapter`() {
        // execution
        val result = specification.toSpringSpecification<Any>()

        // validation
        assertTrue { result is DynamicQuerySpecificationAdapter }
    }

    @Test
    fun `toSpringSpecification without a scope down supplier on a null receiver should return an empty Specification`() {
        // execution
        val result = (null as DynamicQuerySpecification?).toSpringSpecification<Any>()

        // validation
        val predicate = result.toPredicate(root, query, builder)
        assertEquals(emptyPredicate, predicate)
    }

    @Test
    fun `toSpringSpecification with a scope down supplier should invoke and return the value provided by the supplier`() {
        // execution
        val result = specification.toSpringSpecification(scopeDownSupplier)

        // validation
        verify { scopeDownSupplier(any<DynamicQuerySpecificationAdapter<Any>>()) }
        assertEquals(supplierResponse, result)
    }

    @Test
    fun `toSpringSpecification with a scope down supplier on a null receiver invoke supplier with an empty Specification`() {
        // scenario
        val slot = slot<Specification<Any>>()

        // execution
        val result = (null as DynamicQuerySpecification?).toSpringSpecification(scopeDownSupplier)

        // validation
        assertEquals(supplierResponse, result)
        verify { scopeDownSupplier(capture(slot)) }

        val supplierArgumentPredicate = slot.captured.toPredicate(root, query, builder)
        assertEquals(emptyPredicate, supplierArgumentPredicate)
    }

}
