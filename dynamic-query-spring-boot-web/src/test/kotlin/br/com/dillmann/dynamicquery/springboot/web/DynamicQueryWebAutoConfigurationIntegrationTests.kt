package br.com.dillmann.dynamicquery.springboot.web

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertNotNull

/**
 * [DynamicQueryWebAutoConfiguration] integration tests
 */
@ExtendWith(value = [SpringExtension::class])
@ContextConfiguration(classes = [DynamicQueryWebAutoConfiguration::class])
class DynamicQueryWebAutoConfigurationIntegrationTests {

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun `spring context should be able to start with the Dynamic Query resources in the classpath`() {
        assertNotNull(
            context.getBean<DynamicQueryWebAutoConfiguration>()
        )
        assertNotNull(
            context.getBean<DynamicQueryWebErrorHandler>()
        )
        assertNotNull(
            context.getBean<DynamicQueryWebConfigurationProperties>()
        )
        assertNotNull(
            context.getBean<DynamicQueryWebArgumentResolver>()
        )
    }
}
