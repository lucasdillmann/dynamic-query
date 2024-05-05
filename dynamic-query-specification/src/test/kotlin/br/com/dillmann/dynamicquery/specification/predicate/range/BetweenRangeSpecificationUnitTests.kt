package br.com.dillmann.dynamicquery.specification.predicate.range

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [BetweenRangeSpecification] unit tests
 */
class BetweenRangeSpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val startValue = mockk<Parameter>()
    private val endValue = mockk<Parameter>()
    private val startExpression = mockk<Expression<String>>()
    private val endExpression = mockk<Expression<String>>()
    private val path = mockk<Expression<String>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val specification = BetweenRangeSpecification(attributeName, startValue, endValue)

    @BeforeEach
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path
        every { startValue.asExpression(any(), any(), any(), any<Class<String>>()) } returns startExpression
        every { endValue.asExpression(any(), any(), any(), any<Class<String>>()) } returns endExpression
        every { path.javaType } returns String::class.java
        every { builder.between(any<Expression<String>>(), any<Expression<String>>(), any<Expression<String>>()) } returns predicate
    }

    @Test
    fun `toPredicate should resolve and use the target using the given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.between(path, any<Expression<String>>(), any<Expression<String>>()) }
    }

    @Test
    fun `toPredicate should parse the start value to the target type using the given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { startValue.asExpression(root, query, builder, String::class.java) }
        verify { builder.between(any<Expression<String>>(), startExpression, any<Expression<String>>()) }
    }

    @Test
    fun `toPredicate should parse the end value to the target type using the given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { endValue.asExpression(root, query, builder, String::class.java) }
        verify { builder.between(any<Expression<String>>(), any<Expression<String>>(), endExpression) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
