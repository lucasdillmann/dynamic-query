package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.valueparser.EnumTestArtifact
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [EnumValueParser] unit tests
 */
class EnumValueParserUnitTests {

    @Test
    fun `parse should be able to parse the input and return the expected value`() {
        // scenario
        val expectedValue = EnumTestArtifact.ENUM_OPTION
        val inputValue = expectedValue.name

        // execution
        val result = EnumValueParser.parse(inputValue, EnumTestArtifact::class.java)

        // validation
        assertEquals(expectedValue, result)
    }

    @Test
    fun `supports should return true when the type is an Enum`() {
        // execution
        val result = EnumValueParser.supports(randomString, EnumTestArtifact::class.java)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supports should return false when the type is anything but an Enum`() {
        // execution
        val result = EnumValueParser.supports(randomString, Any::class.java)

        // validation
        assertFalse(result)
    }

    @Test
    fun `priority should return the lowest priority possible`() {
        // execution
        val priority = EnumValueParser.priority

        // validation
        assertEquals(Int.MAX_VALUE, priority)
    }
}
