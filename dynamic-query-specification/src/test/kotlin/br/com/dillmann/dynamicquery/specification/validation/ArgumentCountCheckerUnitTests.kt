package br.com.dillmann.dynamicquery.specification.validation

import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.specification.randomString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import kotlin.test.assertEquals

/**
 * [ArgumentCountChecker] unit tests
 */
class ArgumentCountCheckerUnitTests {

    @Test
    fun `it should throw InvalidArgumentCountException when input quantity is greater than the upper bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 21

        // validation
        assertThrows<InvalidArgumentCountException> {

            // execution
            ArgumentCountChecker.check(randomString, range, inputValue)
        }
    }

    @Test
    fun `it should throw InvalidArgumentCountException when input quantity is lower than the lower bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 9

        // validation
        assertThrows<InvalidArgumentCountException> {

            // execution
            ArgumentCountChecker.check(randomString, range, inputValue)
        }
    }

    @Test
    fun `it should throw nothing when input quantity is equal to the lower bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 10

        // execution
        ArgumentCountChecker.check(randomString, range, inputValue)
    }

    @Test
    fun `it should throw nothing when input quantity is equal to the upper bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 20

        // execution
        ArgumentCountChecker.check(randomString, range, inputValue)
    }

    @Test
    fun `it should throw nothing when input quantity is bigger than the lower bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 11

        // execution
        ArgumentCountChecker.check(randomString, range, inputValue)
    }

    @Test
    fun `it should throw nothing when input quantity is lower than the upper bounds`() {
        // scenario
        val range = 10..20
        val inputValue = 19

        // execution
        ArgumentCountChecker.check(randomString, range, inputValue)
    }

    @Test
    fun `thrown InvalidArgumentCountException should have the expected values`() {
        // scenario
        val range = 10..20
        val inputValue = 5
        val type = randomString

        // execution
        try {
            ArgumentCountChecker.check(type, range, inputValue)
            fail("An InvalidArgumentCountException was expected but none was thrown")
        } catch (ex: InvalidArgumentCountException) {
            assertEquals(type, ex.operation)
            assertEquals(inputValue, ex.currentArgumentCount)
            assertEquals(range.first, ex.minimumArgumentCount)
            assertEquals(range.last, ex.maximumArgumentCount)
        }
    }}
