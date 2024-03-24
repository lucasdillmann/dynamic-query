package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomString
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [UuidValueParser] unit tests
 */
class UuidValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = UUID.randomUUID()
        val inputValue = expectedValue.toString()

        // execution
        val result = UuidValueParser.parse(inputValue, UUID::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is UUID`() {
        // execution
        val result = UuidValueParser.supports(randomString, UUID::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but UUID`() {
        // execution
        val result = UuidValueParser.supports(randomString, Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = UuidValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
