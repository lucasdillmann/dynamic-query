package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [ShortValueParser] unit tests
 */
class ShortValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = (Short.MIN_VALUE..Short.MAX_VALUE).random().toShort()
        val inputValue = "$expectedValue"

        // execution
        val result = ShortValueParser.parse(inputValue, Short::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Short`() {
        // execution
        val result = ShortValueParser.supports(randomString, Short::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Short`() {
        // execution
        val result = ShortValueParser.supports(randomString, Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = ShortValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
