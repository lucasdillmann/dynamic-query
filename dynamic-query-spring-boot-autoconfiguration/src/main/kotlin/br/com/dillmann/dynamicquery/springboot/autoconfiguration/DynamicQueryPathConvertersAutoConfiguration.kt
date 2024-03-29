package br.com.dillmann.dynamicquery.springboot.autoconfiguration

import br.com.dillmann.dynamicquery.specification.path.PathConverter
import br.com.dillmann.dynamicquery.specification.path.PathConverters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration

/**
 * Spring Boot autoconfiguration for the Dynamic Query's [PathConverters]
 */
@AutoConfiguration
class DynamicQueryPathConvertersAutoConfiguration {

    /**
     * Install all managed instances of a [PathConverter] in the Dynamic Query's [PathConverters] facade,
     * effectively enabling them to be automatically used in path conversions
     *
     * @param converters Relation of available [PathConverter] managed instances
     */
    @Autowired(required = false)
    fun registerAvailableConverters(converters: List<PathConverter>) {
        converters.forEach(PathConverters::register)
    }
}
