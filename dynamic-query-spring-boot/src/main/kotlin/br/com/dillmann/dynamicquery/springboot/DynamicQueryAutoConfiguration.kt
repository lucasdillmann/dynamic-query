package br.com.dillmann.dynamicquery.springboot

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Manifest class for the Dynamic Query's automatic configuration with the Spring Boot
 */
@Configuration
@ConfigurationPropertiesScan
@ComponentScan(basePackageClasses = [DynamicQueryAutoConfiguration::class])
class DynamicQueryAutoConfiguration
