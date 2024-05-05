package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [IsNotEmptyUnarySpecification] unit tests
 */
class IsNotEmptyUnarySpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val path = mockk<Expression<Collection<Any>>>()
    private val predicate = mockk<Predicate>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val specification = IsNotEmptyUnarySpecification(attributeName)

    @BeforeEach
    @Suppress("UNCHECKED_CAST")
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path as Expression<Any>
        every { builder.isNotEmpty(any<Expression<Collection<Any>>>()) } returns predicate
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.isNotEmpty(path) }
    }

    @Test
    fun `toPredicate should return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
