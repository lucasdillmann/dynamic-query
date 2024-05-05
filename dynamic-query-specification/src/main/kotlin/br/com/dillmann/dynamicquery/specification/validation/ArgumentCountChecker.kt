package br.com.dillmann.dynamicquery.specification.validation

import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException

/**
 * Validation utility for dynamically sized arguments
 */
internal object ArgumentCountChecker {

    /**
     * Checks if the actual number of arguments is within the expected bounds, throwing a
     * [InvalidArgumentCountException] when it's not.
     *
     * @param type Type of the operation being validated. Will be used as the name of the operation if the check fails.
     * @param expected Expected range of argument count
     * @param actual Actual amount of arguments
     */
    fun check(type: String, expected: IntRange, actual: Int) {
        if (actual !in expected)
            throw InvalidArgumentCountException(type, actual, expected.first, expected.last)
    }
}
