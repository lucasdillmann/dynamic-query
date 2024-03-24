package br.com.dillmann.dynamicquery.springboot.web

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.web.method.support.HandlerMethodArgumentResolver

/**
 * [DynamicQueryWebMvcConfigurer] unit tests
 */
class DynamicQueryWebMvcConfigurerUnitTests {

    private val resolver = mockk<DynamicQueryWebArgumentResolver>()
    private val configurer = DynamicQueryWebMvcConfigurer(resolver)

    @Test
    fun `addArgumentResolvers should add the argument resolver to the provided list`() {
        // scenario
        val resolvers = mockk<MutableList<HandlerMethodArgumentResolver>>()
        every { resolvers.add(any()) } returns true

        // execution
        configurer.addArgumentResolvers(resolvers)

        // validation
        verify { resolvers.add(resolver) }
    }
}
