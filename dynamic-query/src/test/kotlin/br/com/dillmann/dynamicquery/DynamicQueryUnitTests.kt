package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.DynamicQueryGrammarParserFactory
import br.com.dillmann.dynamicquery.grammar.converter.GrammarConverter
import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
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

    private val parser = mockk<DynamicQueryGrammarParser>()
    private val rootNode = mockk<TreeNode>()
    private val specification = mockk<DynamicQuerySpecification>()

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkObject(DynamicQueryGrammarParserFactory, DynamicQueryCompiler)
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
        every { DynamicQueryGrammarParserFactory.build(any()) } returns parser
        every { parser.addParseListener(any()) } just Runs
        every { parser.root() } returns mockk()
        every { anyConstructed<GrammarConverter>().rootNode() } returns rootNode
        every { DynamicQueryCompiler.compile(any()) } returns specification
    }

    @Test
    fun `parse should use the provided expression as the expression to be parsed`() {
        // scenario
        val inputValue = randomString

        // execution
        DynamicQuery.parse(inputValue)

        // validation
        verify { DynamicQueryGrammarParserFactory.build(inputValue) }
    }

    @Test
    fun `parse should compile and return the specification using the parsed TreeNode`() {
        // execution
        val result = DynamicQuery.parse(randomString)

        // validation
        assertEquals(specification, result)
        verify { DynamicQueryCompiler.compile(rootNode) }
    }

    @Test
    fun `parse should configure the DSL parser with the expected converter`() {
        // execution
        DynamicQuery.parse(randomString)

        // validation
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
