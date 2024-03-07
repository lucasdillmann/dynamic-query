package br.com.dillmann.dynamicquery.core.valueparser

import br.com.dillmann.dynamicquery.core.valueparser.default.*
import br.com.dillmann.dynamicquery.core.valueparser.exception.NoValueParserAvailableException
import br.com.dillmann.dynamicquery.core.valueparser.exception.ParseFailedException
import java.util.TreeSet

/**
 * Facade for simple conversions between [String] values and any needed target type
 */
object ValueParsers {

    private val parsers = TreeSet<ValueParser<out Any>>(ValueParserComparator)

    init {
        register(BigDecimalValueParser)
        register(BigIntegerValueParser)
        register(BooleanValueParser)
        register(ByteValueParser)
        register(CharArrayValueParser)
        register(DoubleValueParser)
        register(FloatValueParser)
        register(IntValueParser)
        register(LocalDateTimeValueParser)
        register(LocalDateValueParser)
        register(LongValueParser)
        register(OffsetDateTimeValueParser)
        register(OffsetTimeValueParser)
        register(ShortValueParser)
        register(StringValueParser)
        register(UuidValueParser)
        register(ZonedDateTimeValueParser)
    }

    /**
     * Registers the provided [ValueParser] implementation int the relation of available value parsers that
     * this class can use to perform conversions
     *
     * @param parser Implementation to be registered and used to perform value conversion
     */
    @JvmStatic
    fun register(parser: ValueParser<out Any>) {
        parsers.add(parser)
    }

    /**
     * Removes the provided [ValueParser] implementation from the relation of available value parsers that
     * this class can use to perform conversions
     *
     * @param parser Implementation to be removed
     */
    @JvmStatic
    fun deregister(parser: ValueParser<out Any>) {
        parsers.remove(parser)
    }

    /**
     * Parses the given value as a [String] to the requested target type
     *
     * @param value Value to be parsed
     * @param targetType Desired result type
     * @throws NoValueParserAvailableException when no value parser is available to perform the requested conversion
     */
    @JvmStatic
    fun <T> parse(value: String, targetType: Class<T>): T {
        try {
            @Suppress("UNCHECKED_CAST")
            return parsers
                .firstOrNull { it.supports(targetType) }
                ?.parse(value) as? T
                ?: throw NoValueParserAvailableException(value, targetType)
        } catch (ex: NoValueParserAvailableException) {
            throw ex
        } catch (ex: Exception) {
            throw ParseFailedException(value, targetType, ex)
        }
    }

}
