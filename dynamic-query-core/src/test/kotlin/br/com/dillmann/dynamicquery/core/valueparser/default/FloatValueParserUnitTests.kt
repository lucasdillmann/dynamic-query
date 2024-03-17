package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [FloatValueParser] unit tests
 */
class FloatValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = randomInt.toFloat()
        val inputValue = "$expectedValue"

        // execution
        val result = FloatValueParser.parse(inputValue)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Float`() {
        // execution
        val result = FloatValueParser.supports(Float::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Float`() {
        // execution
        val result = FloatValueParser.supports(Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = FloatValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
