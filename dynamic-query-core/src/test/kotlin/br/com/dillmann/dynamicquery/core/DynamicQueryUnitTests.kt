package br.com.dillmann.dynamicquery.core

import br.com.dillmann.dynamicquery.core.grammar.DynamicQueryDslParserFactory
import br.com.dillmann.dynamicquery.core.grammar.converter.GrammarConverter
import br.com.dillmann.dynamicquery.core.grammar.dsl.DynamicQueryDslParser
import br.com.dillmann.dynamicquery.core.specification.Specification
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [DynamicQuery] unit tests
 */
class DynamicQueryUnitTests {

    private val parser = mockk<DynamicQueryDslParser>()
    private val specification = mockk<Specification>()

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkObject(DynamicQueryDslParserFactory)
            mockkConstructor(GrammarConverter::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { DynamicQueryDslParserFactory.build(any()) } returns parser
        every { parser.addParseListener(any()) } just Runs
        every { parser.root() } returns mockk()
        every { anyConstructed<GrammarConverter>().specification() } returns specification
    }

    @Test
    fun `parse should use the provided expression as the expression to be parsed`() {
        // scenario
        val inputValue = randomString

        // execution
        DynamicQuery.parse(inputValue)

        // validation
        verify { DynamicQueryDslParserFactory.build(inputValue) }
    }

    @Test
    fun `parse should configure the DSL parser with the expected converter and return the value provided by it`() {
        // execution
        val result = DynamicQuery.parse(randomString)

        // validation
        assertEquals(specification, result)
        verify { parser.addParseListener(any<GrammarConverter>()) }
    }

    @Test
    fun `parse should should start from the root element`() {
        // execution
        DynamicQuery.parse(randomString)

        // validation
        verify { parser.root() }
    }
}
