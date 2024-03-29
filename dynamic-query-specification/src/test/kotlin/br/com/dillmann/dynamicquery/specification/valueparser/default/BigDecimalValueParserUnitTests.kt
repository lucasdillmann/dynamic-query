package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [BigDecimalValueParser] unit tests
 */
class BigDecimalValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val inputValue = "123.45678"
        val expectedValue = BigDecimal(inputValue)

        // execution
        val result = BigDecimalValueParser.parse(inputValue, BigDecimal::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is BigDecimal`() {
        // execution
        val result = BigDecimalValueParser.supports(randomString, BigDecimal::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but BigDecimal`() {
        // execution
        val result = BigDecimalValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = BigDecimalValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
