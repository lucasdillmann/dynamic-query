package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import org.junit.jupiter.api.Test
import java.time.OffsetTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [OffsetTimeValueParser] unit tests
 */
class OffsetTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = OffsetTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = OffsetTimeValueParser.parse(inputValue)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is OffsetTime`() {
        // execution
        val result = OffsetTimeValueParser.supports(OffsetTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but OffsetTime`() {
        // execution
        val result = OffsetTimeValueParser.supports(Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = OffsetTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
