package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [OffsetDateTimeValueParser] unit tests
 */
class OffsetDateTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = OffsetDateTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = OffsetDateTimeValueParser.parse(inputValue)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is OffsetDateTime`() {
        // execution
        val result = OffsetDateTimeValueParser.supports(OffsetDateTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but OffsetDateTime`() {
        // execution
        val result = OffsetDateTimeValueParser.supports(Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = OffsetDateTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
