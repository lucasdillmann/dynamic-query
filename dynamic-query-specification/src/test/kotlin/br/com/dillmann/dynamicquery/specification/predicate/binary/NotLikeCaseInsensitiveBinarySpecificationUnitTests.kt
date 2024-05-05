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
 * [NotLikeCaseInsensitiveBinarySpecification] unit tests
 */
class NotLikeCaseInsensitiveBinarySpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val value = mockk<Parameter>()
    private val valueExpression = mockk<Expression<String>>()
    private val path = mockk<Expression<String>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val lowerExpression = mockk<Expression<String>>()
    private val specification = NotLikeCaseInsensitiveBinarySpecification(attributeName, value)

    @BeforeEach
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path
        every { value.asExpression(any(), any(), any(), any<Class<*>>()) } returns valueExpression
        every { builder.notLike(any<Expression<String>>(), any<Expression<String>>()) } returns predicate
        every { builder.lower(any()) } returns lowerExpression
        every { path.javaType } returns String::class.java
        every { valueExpression.javaType } returns String::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.lower(path) }
        verify { builder.notLike(lowerExpression, any<Expression<String>>()) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { value.asExpression(root, query, builder, String::class.java) }
        verify { builder.lower(valueExpression) }
        verify { builder.notLike(any(), lowerExpression) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
        verify { builder.lower(any<Expression<String>>()) }
        verify { builder.notLike(any<Expression<String>>(), any<Expression<String>>()) }
    }
}
