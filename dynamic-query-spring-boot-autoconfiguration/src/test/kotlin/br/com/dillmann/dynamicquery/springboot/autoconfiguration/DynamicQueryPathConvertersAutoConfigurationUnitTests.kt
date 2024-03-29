package br.com.dillmann.dynamicquery.springboot.autoconfiguration

import br.com.dillmann.dynamicquery.specification.path.PathConverter
import br.com.dillmann.dynamicquery.specification.path.PathConverters
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 * [DynamicQueryPathConvertersAutoConfiguration] unit tests
 */
class DynamicQueryPathConvertersAutoConfigurationUnitTests {

    private val autoConfiguration = DynamicQueryPathConvertersAutoConfiguration()

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkStatic(PathConverters::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @Test
    fun `registerAvailableConverters should register all provided converters in the PathConverters`() {
        // scenario
        val converter1 = buildConverterMock()
        val converter2 = buildConverterMock()
        val converter3 = buildConverterMock()

        // execution
        autoConfiguration.registerAvailableConverters(
            listOf(converter1, converter2, converter3)
        )

        // validation
        verify { PathConverters.register(converter1) }
        verify { PathConverters.register(converter2) }
        verify { PathConverters.register(converter3) }
    }

    private fun buildConverterMock() =
        mockk<PathConverter> {
            every { priority } returns 1
        }
}
