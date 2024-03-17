package br.com.dillmann.dynamicquery.springboot.web

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration properties for the Dynamic Query's integration with the Spring Web APIs
 */
@ConfigurationProperties(prefix = "dynamic-query.web")
class DynamicQueryWebConfigurationProperties {

    /**
     * Name of the query parameter where the Dynamic Query DSL will be available on HTTP requests. Value will be
     * used when the Dynamic Query's Specification is injected on a MVC Controller (via method arguments).
     *
     * When not set, the [DEFAULT_HTTP_QUERY_PARAMETER_NAME] will be used as the fallback value.
     */
    var queryParameterName: String? = null

    /**
     * Defines if the Dynamic Query's error handler for the Spring Web controllers should be enabled or not. When
     * not set or set to true, a HTTP response will be automatically generated with the error details whenever
     * a Dynamic Query's exception is thrown.
     */
    var enableErrorHandler: Boolean? = null
}
