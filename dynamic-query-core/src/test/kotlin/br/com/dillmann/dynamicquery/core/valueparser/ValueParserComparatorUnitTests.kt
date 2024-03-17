package br.com.dillmann.dynamicquery.core.valueparser

import br.com.dillmann.dynamicquery.core.randomInt
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [ValueParserComparator] unit tests
 */
class ValueParserComparatorUnitTests {

    private val leftParser = mockk<ValueParser<Any>>()
    private val rightParser = mockk<ValueParser<Any>>()

    @Test
    fun `compare should return zero when both parsers have the same priority`() {
        // scenario
        val priority = randomInt
        every { leftParser.priority } returns priority
        every { rightParser.priority } returns priority

        // execution
        val result = ValueParserComparator.compare(leftParser, rightParser)

        // validation
        assertEquals(0, result)
    }

    @Test
    fun `compare should return -1 when the left parser has a higher priority`() {
        // scenario
        every { leftParser.priority } returns 0
        every { rightParser.priority } returns 1

        // execution
        val result = ValueParserComparator.compare(leftParser, rightParser)

        // validation
        assertEquals(-1, result)
    }

    @Test
    fun `compare should return 1 when the right parser has a higher priority`() {
        // scenario
        every { leftParser.priority } returns 1
        every { rightParser.priority } returns 0

        // execution
        val result = ValueParserComparator.compare(leftParser, rightParser)

        // validation
        assertEquals(1, result)
    }
}
