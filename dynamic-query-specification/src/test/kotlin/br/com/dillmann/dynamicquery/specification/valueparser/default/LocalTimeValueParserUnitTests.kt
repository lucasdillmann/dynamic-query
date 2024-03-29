package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomInt
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [LocalTimeValueParser] unit tests
 */
class LocalTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = LocalTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = LocalTimeValueParser.parse(inputValue, LocalTime::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is LocalTime`() {
        // execution
        val result = LocalTimeValueParser.supports(randomString, LocalTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but LocalTime`() {
        // execution
        val result = LocalTimeValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = LocalTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
