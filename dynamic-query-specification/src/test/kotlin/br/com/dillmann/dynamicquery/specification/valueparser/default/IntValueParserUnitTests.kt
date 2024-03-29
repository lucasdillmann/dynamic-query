package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomInt
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [IntValueParser] unit tests
 */
class IntValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = randomInt
        val inputValue = "$expectedValue"

        // execution
        val result = IntValueParser.parse(inputValue, Int::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is Int`() {
        // execution
        val result = IntValueParser.supports(randomString, Int::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but Int`() {
        // execution
        val result = IntValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = IntValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
