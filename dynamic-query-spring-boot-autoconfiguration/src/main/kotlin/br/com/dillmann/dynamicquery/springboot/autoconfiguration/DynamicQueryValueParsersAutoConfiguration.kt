package br.com.dillmann.dynamicquery.springboot.autoconfiguration

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration

/**
 * Spring Boot autoconfiguration for the Dynamic Query's [ValueParsers]
 */
@AutoConfiguration
class DynamicQueryValueParsersAutoConfiguration {

    /**
     * Install all managed instances of a [ValueParser] in the Dynamic Query's [ValueParsers] facade,
     * effectively enabling them to be automatically used in path conversions
     *
     * @param parsers Relation of available [ValueParser] managed instances
     */
    @Autowired(required = false)
    fun registerAvailableParsers(parsers: List<ValueParser<out Any>>) {
        parsers.forEach(ValueParsers::register)
    }
}
