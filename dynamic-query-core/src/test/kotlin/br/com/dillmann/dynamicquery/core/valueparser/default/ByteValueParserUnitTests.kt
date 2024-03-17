package br.com.dillmann.dynamicquery.core.valueparser.default

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [ByteValueParser] unit tests
 */
class ByteValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = (Byte.MIN_VALUE..Byte.MAX_VALUE).random().toByte()
        val inputValue = "$expectedValue"

        // execution
        val result = ByteValueParser.parse(inputValue)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Byte`() {
        // execution
        val result = ByteValueParser.supports(Byte::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Byte`() {
        // execution
        val result = ByteValueParser.supports(Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = ByteValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
