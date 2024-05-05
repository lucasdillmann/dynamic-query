package br.com.dillmann.dynamicquery.specification.decorators

import br.com.dillmann.dynamicquery.specification.exception.UnsupportedValueTypeException
import br.com.dillmann.dynamicquery.specification.randomString
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * [caseInsensitive] unit tests
 */
class CaseInsensitiveUnitTests {

    private val path = mockk<Expression<Comparable<Any>>>()
    private val lowerExpression = mockk<Expression<String>>()
    private val predicate = mockk<Predicate>()
    private val builder = mockk<CriteriaBuilder>()

    @BeforeEach
    fun setUp() {
        every { builder.equal(any(), any<Expression<Any>>()) } returns predicate
        every { builder.lower(any<Expression<String>>()) } returns lowerExpression
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `caseInsensitive should encapsulate the operation by applying lower on both path and value`() {
        // scenario
        val inputValue = mockk<Expression<String>>()
        every { inputValue.javaType } returns String::class.java

        // execution
        val decoratedEqual = caseInsensitive(randomString, CriteriaBuilder::equal)
        val result = builder.decoratedEqual(path, inputValue as Expression<Comparable<Any>>)

        // validation
        assertEquals(predicate, result)
        verify { builder.lower(path as Expression<String>) }
        verify { builder.lower(inputValue) }
        verify { builder.equal(lowerExpression, lowerExpression) }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `caseInsensitive should throw UnsupportedValueTypeException when value isn't a string`() {
        // scenario
        val operationName = randomString
        val inputValue = mockk<Expression<OffsetDateTime>>()
        every { inputValue.javaType } returns OffsetDateTime::class.java

        // execution
        val result = runCatching {
            val decoratedEqual = caseInsensitive(operationName, CriteriaBuilder::equal)
            builder.decoratedEqual(path, inputValue as Expression<Comparable<Any>>)
        }

        // validation
        val exception = result.exceptionOrNull() as? UnsupportedValueTypeException
        assertNotNull(exception)
        assertEquals(path, exception.expression)
        assertEquals(operationName, exception.operationName)
        assertEquals(inputValue, exception.value)
    }
}
