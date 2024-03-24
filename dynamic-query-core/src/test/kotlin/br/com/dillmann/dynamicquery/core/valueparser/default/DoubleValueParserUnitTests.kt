package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomInt
import br.com.dillmann.dynamicquery.core.randomString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [DoubleValueParser] unit tests
 */
class DoubleValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = randomInt.toDouble()
        val inputValue = "$expectedValue"

        // execution
        val result = DoubleValueParser.parse(inputValue, Double::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Double`() {
        // execution
        val result = DoubleValueParser.supports(randomString, Double::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Double`() {
        // execution
        val result = DoubleValueParser.supports(randomString, Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = DoubleValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
