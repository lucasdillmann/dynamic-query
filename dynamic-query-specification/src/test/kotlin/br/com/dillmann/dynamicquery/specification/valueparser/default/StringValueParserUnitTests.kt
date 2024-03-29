package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [StringValueParser] unit tests
 */
class StringValueParserUnitTests {

    @Test
    fun `parse should return the input value unchanged`() {
        // scenario
        val inputValue = randomString

        // execution
        val result = StringValueParser.parse(inputValue, String::class.java)

        // validation
        assertEquals(inputValue, result)
    }

    @Test
    fun `supports should return true when the type is String`() {
        // execution
        val result = StringValueParser.supports(randomString, String::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but String`() {
        // execution
        val result = StringValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = StringValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
