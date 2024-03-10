package br.com.dillmann.dynamicquery.core.specification.decorators

import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.specification.exception.UnsupportedValueTypeException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * [requireString] unit tests
 */
class RequireStringUnitTests {

    private val path = mockk<Path<Comparable<Any>>>()
    private val predicate = mockk<Predicate>()
    private val builder = mockk<CriteriaBuilder>()

    @BeforeEach
    fun setUp() {
        every { builder.equal(any(), any<String>()) } returns predicate
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `requireString should encapsulate the operation by converting the generic type`() {
        // scenario
        val inputValue = randomString

        // execution
        val decoratedEqual = requireString(randomString, CriteriaBuilder::equal)
        val result = builder.decoratedEqual(path, inputValue as Comparable<Any>)

        // validation
        assertEquals(predicate, result)
        verify { builder.equal(path, inputValue) }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `requireString should throw UnsupportedValueTypeException when value isn't a string`() {
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
