package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
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
        val result = ByteValueParser.parse(inputValue, Byte::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Byte`() {
        // execution
        val result = ByteValueParser.supports(randomString, Byte::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Byte`() {
        // execution
        val result = ByteValueParser.supports(randomString, TestType::class.java)

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
