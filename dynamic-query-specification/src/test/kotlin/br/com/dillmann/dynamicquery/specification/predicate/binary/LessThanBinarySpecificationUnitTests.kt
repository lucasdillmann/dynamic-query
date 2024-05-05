package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [LessThanBinarySpecification] unit tests
 */
class LessThanBinarySpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val value = mockk<Parameter>()
    private val valueExpression = mockk<Expression<Long>>()
    private val path = mockk<Expression<Long>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val specification = LessThanBinarySpecification(attributeName, value)

    @BeforeEach
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path
        every { value.asExpression(any(), any(), any(), any<Class<*>>()) } returns valueExpression
        every { builder.lessThan(any<Expression<Long>>(), any<Expression<Long>>()) } returns predicate
        every { path.javaType } returns Long::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.lessThan(path, any<Expression<Long>>()) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { value.asExpression(root, query, builder, Long::class.java) }
        verify { builder.lessThan(any(), valueExpression) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
        verify { builder.lessThan(any<Expression<Long>>(), any<Expression<Long>>()) }
    }
}
