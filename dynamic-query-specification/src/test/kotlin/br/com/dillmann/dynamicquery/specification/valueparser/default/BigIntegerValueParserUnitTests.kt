package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.randomInt
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.TestType
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [BigIntegerValueParser] unit tests
 */
class BigIntegerValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val inputValue = "$randomInt"
        val expectedValue = BigInteger(inputValue)

        // execution
        val result = BigIntegerValueParser.parse(inputValue, BigInteger::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is BigInteger`() {
        // execution
        val result = BigIntegerValueParser.supports(randomString, BigInteger::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but BigInteger`() {
        // execution
        val result = BigIntegerValueParser.supports(randomString, TestType::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = BigIntegerValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
