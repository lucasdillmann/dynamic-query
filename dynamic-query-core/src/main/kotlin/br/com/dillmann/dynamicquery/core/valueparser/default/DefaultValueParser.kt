package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import kotlin.reflect.KClass

/**
 * Generic and internal abstract implementation of a [ValueParser] for the default value parsers. This class is intended
 * for internal use only.
 *
 * @param supportedType Type of values that the implementation supports
 */
internal abstract class DefaultValueParser<T: Any>(private val supportedType: KClass<T>): ValueParser<T> {

    /**
     * Priority of the parser. Always returns [Int.MAX_VALUE] (which translates to the lowest possible priority) since
     * this is a default implementation that can be overwritten.
     */
    override val priority: Int =
        Int.MAX_VALUE

    /**
     * Checks if the parser supports the conversion from [String] to a specific target type
     *
     * @param targetType Resulting type of the conversion that is about to happen
     */
    override fun supports(targetType: Class<*>): Boolean =
        supportedType.java.isAssignableFrom(targetType)
}
