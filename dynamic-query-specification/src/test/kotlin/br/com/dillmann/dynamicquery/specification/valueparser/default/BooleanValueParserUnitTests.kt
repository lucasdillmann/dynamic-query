package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomBoolean
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [BooleanValueParser] unit tests
 */
class BooleanValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = randomBoolean
        val inputValue = "$expectedValue"

        // execution
        val result = BooleanValueParser.parse(inputValue, Boolean::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Boolean`() {
        // execution
        val result = BooleanValueParser.supports(randomString, Boolean::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Boolean`() {
        // execution
        val result = BooleanValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = BooleanValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
