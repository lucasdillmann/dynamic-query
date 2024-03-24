package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.valueparser.TestType
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [LocalDateTimeValueParser] unit tests
 */
class LocalDateTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = LocalDateTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = LocalDateTimeValueParser.parse(inputValue, LocalDateTime::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is LocalDateTime`() {
        // execution
        val result = LocalDateTimeValueParser.supports(randomString, LocalDateTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but LocalDateTime`() {
        // execution
        val result = LocalDateTimeValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = LocalDateTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
