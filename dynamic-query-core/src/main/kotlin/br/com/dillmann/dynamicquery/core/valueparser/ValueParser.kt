package br.com.dillmann.dynamicquery.core.valueparser

/**
 * Definition of a value parsed able to convert a [String] value to a specific, domain value
 */
interface ValueParser<T> {

    /**
     * Priority of the parsers. Lower values means higher priorities.
     */
    val priority: Int

    /**
     * Checks if the parser supports the conversion from [String] to a specific target type
     *
     * @param targetType Resulting type of the conversion that is about to happen
     */
    fun supports(targetType: Class<*>): Boolean

    /**
     * Parses the given [String] value as an instance of the domain value
     *
     * @param value Value to be parsed
     */
    fun parse(value: String): T?
}
