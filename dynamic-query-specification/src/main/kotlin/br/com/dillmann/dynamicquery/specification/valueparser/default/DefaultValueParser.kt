package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser
import kotlin.reflect.KClass

/**
 * Generic and internal abstract implementation of a [ValueParser] for the default value parsers. This class is intended
 * for internal use only.
 *
 * @param T Generic type of the value supported by the parser
 * @param supportedType Type of values that the implementation supports
 */
internal abstract class DefaultValueParser<T : Any>(private val supportedType: KClass<T>) : ValueParser<T> {

    /**
     * Priority of the parser. Always returns [Int.MAX_VALUE] (which translates to the lowest possible priority) since
     * this is a default implementation that can be overwritten.
     */
    override val priority: Int =
        Int.MAX_VALUE

    /**
     * Checks if the parser supports the conversion from [String] to a specific target type
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun supports(value: String, targetType: Class<*>): Boolean =
        targetType.isAssignableFrom(supportedType.java) || targetType.isAssignableFrom(supportedType.javaObjectType)
}
