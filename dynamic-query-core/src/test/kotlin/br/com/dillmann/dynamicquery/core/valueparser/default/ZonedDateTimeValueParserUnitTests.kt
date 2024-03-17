package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [ZonedDateTimeValueParser] unit tests
 */
class ZonedDateTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = ZonedDateTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = ZonedDateTimeValueParser.parse(inputValue)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is ZonedDateTime`() {
        // execution
        val result = ZonedDateTimeValueParser.supports(ZonedDateTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but ZonedDateTime`() {
        // execution
        val result = ZonedDateTimeValueParser.supports(Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = ZonedDateTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
