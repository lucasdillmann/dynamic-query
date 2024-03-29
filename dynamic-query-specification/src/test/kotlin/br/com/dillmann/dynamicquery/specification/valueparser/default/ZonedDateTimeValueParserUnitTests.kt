package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomInt
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [ZonedDateTimeValueParser] unit tests
 */
class ZonedDateTimeValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = ZonedDateTime.now().plusNanos(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = ZonedDateTimeValueParser.parse(inputValue, ZonedDateTime::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is ZonedDateTime`() {
        // execution
        val result = ZonedDateTimeValueParser.supports(randomString, ZonedDateTime::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but ZonedDateTime`() {
        // execution
        val result = ZonedDateTimeValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = ZonedDateTimeValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
