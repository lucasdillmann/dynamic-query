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
 * [requireString] unit tests
 */
class RequireStringUnitTests {

    private val path = mockk<Expression<Comparable<Any>>>()
    private val predicate = mockk<Predicate>()
    private val builder = mockk<CriteriaBuilder>()

    @BeforeEach
    fun setUp() {
        every { builder.equal(any(), any<Expression<Any>>()) } returns predicate
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `requireString should encapsulate the operation by converting the generic type`() {
        // scenario
        val inputValue = mockk<Expression<String>>()
        every { inputValue.javaType } returns String::class.java

        // execution
        val decoratedEqual = requireString(randomString, CriteriaBuilder::equal)
        val result = builder.decoratedEqual(path, inputValue as Expression<Comparable<Any>>)

        // validation
        assertEquals(predicate, result)
        verify { builder.equal(path, inputValue) }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `requireString should throw UnsupportedValueTypeException when value isn't a string`() {
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
