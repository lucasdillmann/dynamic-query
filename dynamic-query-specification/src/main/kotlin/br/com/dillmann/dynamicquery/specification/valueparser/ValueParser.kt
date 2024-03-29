package br.com.dillmann.dynamicquery.specification.valueparser

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
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    fun supports(value: String, targetType: Class<*>): Boolean

    /**
     * Parses the given [String] value as an instance of the domain value
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    fun parse(value: String, targetType: Class<*>): T?
}
