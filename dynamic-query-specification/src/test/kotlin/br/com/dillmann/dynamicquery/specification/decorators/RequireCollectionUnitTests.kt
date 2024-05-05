package br.com.dillmann.dynamicquery.specification.decorators

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [requireCollection] unit tests
 */
class RequireCollectionUnitTests {

    private val path = mockk<Expression<Collection<Any>>>()
    private val predicate = mockk<Predicate>()
    private val builder = mockk<CriteriaBuilder>()

    @BeforeEach
    fun setUp() {
        every { builder.isEmpty(any<Expression<Collection<Any>>>()) } returns predicate
    }

    @Test
    fun `requireCollection should encapsulate the operation by converting the generic type`() {
        // execution
        val decoratedEqual = requireCollection(CriteriaBuilder::isEmpty)
        val result = builder.decoratedEqual(path)

        // validation
        assertEquals(predicate, result)
        verify { builder.isEmpty(path) }
    }
}
