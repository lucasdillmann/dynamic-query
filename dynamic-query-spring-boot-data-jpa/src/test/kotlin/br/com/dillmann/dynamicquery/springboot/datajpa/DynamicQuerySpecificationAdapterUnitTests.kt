package br.com.dillmann.dynamicquery.springboot.datajpa

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [DynamicQuerySpecificationAdapter] unit tests
 */
class DynamicQuerySpecificationAdapterUnitTests {

    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val delegate = mockk<DynamicQuerySpecification>()
    private val adapter = DynamicQuerySpecificationAdapter<Any>(delegate)

    @BeforeEach
    fun setUp() {
        every { delegate.toPredicate(root, query, builder) } returns predicate
    }

    @Test
    fun `toPredicate should invoke the delegate specification with the expected values`() {
        // execution
        adapter.toPredicate(root, query, builder)

        // validation
        verify { delegate.toPredicate(root, query, builder) }
    }

    @Test
    fun `toPredicate should return the predicate provided by the delegate`() {
        // execution
        val result = adapter.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
