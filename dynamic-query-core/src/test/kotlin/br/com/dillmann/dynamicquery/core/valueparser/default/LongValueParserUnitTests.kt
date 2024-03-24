package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomLong
import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.valueparser.TestType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [LongValueParser] unit tests
 */
class LongValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = randomLong
        val inputValue = "$expectedValue"

        // execution
        val result = LongValueParser.parse(inputValue, Long::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Long`() {
        // execution
        val result = LongValueParser.supports(randomString, Long::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Long`() {
        // execution
        val result = LongValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = LongValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
