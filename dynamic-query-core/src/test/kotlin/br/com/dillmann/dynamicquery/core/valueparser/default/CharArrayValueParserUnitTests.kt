package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomString
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [CharArrayValueParser] unit tests
 */
class CharArrayValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val inputValue = randomString
        val expectedValue = inputValue.toCharArray()

        // execution
        val result = CharArrayValueParser.parse(inputValue, CharArray::class.java)

        // validation
        assertContentEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is CharArray`() {
        // execution
        val result = CharArrayValueParser.supports(randomString, CharArray::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but CharArray`() {
        // execution
        val result = CharArrayValueParser.supports(randomString, Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = CharArrayValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
