package br.com.dillmann.dynamicquery.springboot

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import br.com.dillmann.dynamicquery.core.valueparser.ValueParsers
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 * [DynamicQueryValueParsersAutoConfiguration] unit tests
 */
class DynamicQueryValueParsersAutoConfigurationUnitTests {

    private val autoConfiguration = DynamicQueryValueParsersAutoConfiguration()

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkStatic(ValueParsers::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @Test
    fun `registerAvailableParsers should register all provided converters in the PathConverters`() {
        // scenario
        val parser1 = buildParserMock()
        val parser2 = buildParserMock()
        val parser3 = buildParserMock()

        // execution
        autoConfiguration.registerAvailableParsers(
            listOf(parser1, parser2, parser3)
        )

        // validation
        verify { ValueParsers.register(parser1) }
        verify { ValueParsers.register(parser2) }
        verify { ValueParsers.register(parser3) }
    }

    private fun buildParserMock() =
        mockk<ValueParser<out Any>> {
            every { priority } returns 1
        }
}
