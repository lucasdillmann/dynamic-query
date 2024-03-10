package br.com.dillmann.dynamicquery.core.specification.decorators

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import kotlin.test.assertEquals

/**
 * [requireCollection] unit tests
 */
class RequireCollectionUnitTests {

    private val path = mockk<Path<Collection<Any>>>()
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
