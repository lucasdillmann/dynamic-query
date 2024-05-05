package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

/**
 * [EqualsBinarySpecification] unit tests
 */
class EqualsBinarySpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val value = mockk<Parameter>()
    private val valueExpression = mockk<Expression<Any>>()
    private val path = mockk<Expression<Any>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val specification = EqualsBinarySpecification(attributeName, value)

    @BeforeEach
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path
        every { value.asExpression(any(), any(), any(), any<Class<*>>()) } returns valueExpression
        every { builder.equal(any<Expression<Any>>(), any<Expression<Any>>()) } returns predicate
        every { path.javaType } returns Any::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using the given Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.equal(path, any<Expression<String>>()) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using the given Parameters`() {
        // scenario
        val expectedType = listOf(String::class, Long::class, BigDecimal::class).random().java
        every { path.javaType } returns expectedType

        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { value.asExpression(root, query, builder, expectedType) }
        verify { builder.equal(any(), valueExpression) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
        verify { builder.equal(any<Expression<Any>>(), any<Expression<String>>()) }
    }
}
