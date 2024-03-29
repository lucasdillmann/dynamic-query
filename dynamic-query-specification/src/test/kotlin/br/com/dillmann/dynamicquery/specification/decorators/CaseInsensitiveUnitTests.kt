package br.com.dillmann.dynamicquery.specification.decorators

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.exception.UnsupportedValueTypeException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * [caseInsensitive] unit tests
 */
class CaseInsensitiveUnitTests {

    private val path = mockk<Path<Comparable<Any>>>()
    private val lowerExpression = mockk<Expression<String>>()
    private val predicate = mockk<Predicate>()
    private val builder = mockk<CriteriaBuilder>()

    @BeforeEach
    fun setUp() {
        every { builder.equal(any(), any<String>()) } returns predicate
        every { builder.lower(any<Path<String>>()) } returns lowerExpression
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `caseInsensitive should encapsulate the operation by applying lower on both path and value`() {
        // scenario
        val inputValue = randomString

        // execution
        val decoratedEqual = caseInsensitive(randomString, CriteriaBuilder::equal)
        val result = builder.decoratedEqual(path, inputValue as Comparable<Any>)

        // validation
        assertEquals(predicate, result)
        verify { builder.lower(path as Path<String>) }
        verify { builder.equal(lowerExpression, inputValue.lowercase()) }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `caseInsensitive should throw UnsupportedValueTypeException when value isn't a string`() {
        // scenario
        val operationName = randomString
        val inputValue = OffsetDateTime.now()

        // execution
        val result = runCatching {
            val decoratedEqual = caseInsensitive(operationName, CriteriaBuilder::equal)
            builder.decoratedEqual(path, inputValue as Comparable<Any>)
        }

        // validation
        val exception = result.exceptionOrNull() as? UnsupportedValueTypeException
        assertNotNull(exception)
        assertEquals(path, exception.expression)
        assertEquals(operationName, exception.operationName)
        assertEquals(inputValue, exception.value)
    }
}
