package br.com.dillmann.dynamicquery.springbootweb

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Manifest class for the Dynamic Query's automatic configuration with the Spring Web APIs
 */
@Configuration
@ConfigurationPropertiesScan
@ComponentScan(basePackageClasses = [DynamicQueryWebAutoConfiguration::class])
class DynamicQueryWebAutoConfiguration
