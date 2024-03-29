package br.com.dillmann.dynamicquery.springboot.web

import br.com.dillmann.dynamicquery.DynamicQuery
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * [DynamicQueryWebArgumentResolver] unit tests
 */
class DynamicQueryWebArgumentResolverUnitTests {

    private val configuration = mockk<DynamicQueryWebConfigurationProperties>()
    private val parameter = mockk<MethodParameter>()
    private val specification = mockk<DynamicQuerySpecification>()
    private val webRequest = mockk<NativeWebRequest>()
    private val resolver = DynamicQueryWebArgumentResolver(configuration)

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            mockkObject(DynamicQuery)
            mockkStatic(DynamicQuery::class)
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { DynamicQuery.parse(any()) } returns specification
        every { webRequest.getParameter(any()) } returns randomString
        every { configuration.queryParameterName } returns randomString
        every { parameter.parameterType } returns DynamicQuerySpecification::class.java
    }

    @Test
    fun `supportsParameter should return true when the type of the parameter is DynamicQuerySpecification`() {
        // execution
        val result = resolver.supportsParameter(parameter)

        // validation
        assertTrue(result)
    }

    @Test
    fun `supportsParameter should return false when the type of the parameter is anything but DynamicQuerySpecification`() {
        // scenario
        every { parameter.parameterType } returns Any::class.java

        // execution
        val result = resolver.supportsParameter(parameter)

        // validation
        assertFalse(result)
    }

    @Test
    fun `resolveArgument should return null when no value is available for the dynamic query's parameter`() {
        // scenario
        every { webRequest.getParameter(any()) } returns null

        // execution
        val result = resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        assertNull(result)
    }

    @Test
    fun `resolveArgument should return null when a empty string is found for the dynamic query's parameter`() {
        // scenario
        every { webRequest.getParameter(any()) } returns ""

        // execution
        val result = resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        assertNull(result)
    }

    @Test
    fun `resolveArgument should return null when a blank string is found for the dynamic query's parameter`() {
        // scenario
        every { webRequest.getParameter(any()) } returns "   "

        // execution
        val result = resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        assertNull(result)
    }

    @Test
    fun `resolveArgument should retrieve the parameter value using the key available in the configuration properties`() {
        // scenario
        val expectedKey = randomString
        every { configuration.queryParameterName } returns expectedKey

        // execution
        resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        verify { webRequest.getParameter(expectedKey) }
    }

    @Test
    fun `resolveArgument should retrieve the parameter value using the default key when no key is available in the configuration properties`() {
        // scenario
        every { configuration.queryParameterName } returns null

        // execution
        resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        verify { webRequest.getParameter(DEFAULT_HTTP_QUERY_PARAMETER_NAME) }
    }

    @Test
    fun `resolveArgument should parse and return the specification using the DynamicQuery facade`() {
        // scenario
        val expression = randomString
        every { webRequest.getParameter(any()) } returns expression

        // execution
        val result = resolver.resolveArgument(parameter, mockk(), webRequest, mockk())

        // validation
        verify { DynamicQuery.parse(expression) }
        assertEquals(specification, result)
    }
}
