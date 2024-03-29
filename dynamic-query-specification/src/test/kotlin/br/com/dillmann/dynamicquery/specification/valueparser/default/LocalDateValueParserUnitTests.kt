package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomInt
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [LocalDateValueParser] unit tests
 */
class LocalDateValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = LocalDate.now().plusDays(randomInt.toLong())
        val inputValue = expectedValue.toString()

        // execution
        val result = LocalDateValueParser.parse(inputValue, LocalDate::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is LocalDate`() {
        // execution
        val result = LocalDateValueParser.supports(randomString, LocalDate::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but LocalDate`() {
        // execution
        val result = LocalDateValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = LocalDateValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
