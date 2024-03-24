package br.com.dillmann.dynamicquery.springboot.web

import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * [WebMvcConfigurer] implementation for the Dynamic Query autoconfiguration
 *
 * @param argumentResolver Dynamic Query's argument resolver
 */
@Component
class DynamicQueryWebMvcConfigurer(private val argumentResolver: DynamicQueryWebArgumentResolver): WebMvcConfigurer {

    /**
     * Installs the Dynamic Query's argument resolver in the Spring context
     */
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers += argumentResolver
    }
}
