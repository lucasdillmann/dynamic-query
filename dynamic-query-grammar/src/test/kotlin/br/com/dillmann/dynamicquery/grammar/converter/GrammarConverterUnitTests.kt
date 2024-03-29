package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser.*
import br.com.dillmann.dynamicquery.grammar.randomBoolean
import br.com.dillmann.dynamicquery.grammar.randomListOf
import br.com.dillmann.dynamicquery.grammar.randomLong
import br.com.dillmann.dynamicquery.grammar.randomString
import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [GrammarConverter] unit tests
 */
class GrammarConverterUnitTests {

    private val currentNode = mockk<TreeNode>()
    private val rootNode = mockk<TreeNode>()
    private lateinit var converter: GrammarConverter

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkConstructor(GrammarConversionContext::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { anyConstructed<GrammarConversionContext>().startNode(any()) } just Runs
        every { anyConstructed<GrammarConversionContext>().endNode() } just Runs
        every { anyConstructed<GrammarConversionContext>().currentNode } returns currentNode
        every { anyConstructed<GrammarConversionContext>().root } returns rootNode
        every { currentNode.operation = any() } just Runs
        every { currentNode.attributeName = any() } just Runs
        every { currentNode.parameters = any() } just Runs
        every { currentNode.logicalOperator = any() } just Runs
        every { currentNode.parameters } returns emptyList()

        converter = GrammarConverter()
    }

    @Test
    fun `rootNote should return the root of the tree`() {
        // execution
        val result = converter.rootNode()

        // validation
        assertEquals(rootNode, result)
    }

    @Test
    fun `enterExpression should start a new group node in the internal tree`() {
        // execution
        converter.enterExpression(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.GROUP) }
    }

    @Test
    fun `exitExpression should close the current node in the internal tree`() {
        // execution
        converter.exitExpression(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().endNode() }
    }

    @Test
    fun `enterPredicate should start a new filter node in the internal tree`() {
        // execution
        converter.enterPredicate(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PREDICATE) }
    }

    @Test
    fun `exitPredicate should close the current node in the internal tree`() {
        // execution
        converter.exitPredicate(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().endNode() }
    }

    @Test
    fun `enterNegation should start a new negation node in the internal tree`() {
        // execution
        converter.enterNegation(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.NEGATION) }
    }

    @Test
    fun `exitNegation should close the current node in the internal tree`() {
        // execution
        converter.exitNegation(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().endNode() }
    }

    @Test
    fun `exitLogicalOperator should add and close a new node in the internal tree with the expected type and received operator`() {
        // scenario
        val parserContext = mockk<LogicalOperatorContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitLogicalOperator(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.LOGICAL_OPERATOR)
            currentNode.logicalOperator = expectedValue
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }

    @Test
    fun `exitAttributeName should set the received attribute name in the current tree node`() {
        // scenario
        val parserContext = mockk<AttributeNameContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitAttributeName(parserContext)

        // validation
        verify { currentNode.attributeName = expectedValue }
    }

    @Test
    fun `exitOperation should set the received operation name in the current tree node`() {
        // scenario
        val parserContext = mockk<OperationContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitOperation(parserContext)

        // validation
        verify { currentNode.operation = expectedValue }
    }

    @Test
    fun `enterParameters should initialize the parameter list of the current node as a empty list`() {
        // execution
        converter.enterParameters(mockk())

        // validation
        verify { currentNode.parameters = emptyList() }
    }

    @Test
    fun `exitParameterStringValue should append the received value in the current node parameter list without the surrounding quotes`() {
        // scenario
        val parserContext = mockk<ParameterStringValueContext>()
        val expectedValue = randomString
        val previousValue = randomListOf(minimumSize = 0) { randomString }
        every { parserContext.text } returns "\"$expectedValue\""
        every { currentNode.parameters } returns previousValue

        // execution
        converter.exitParameterStringValue(parserContext)

        // validation
        verify { currentNode.parameters = previousValue + expectedValue }
    }

    @Test
    fun `exitParameterNumericValue should append the received value in the current node parameter list without the surrounding quotes`() {
        // scenario
        val parserContext = mockk<ParameterNumericValueContext>()
        val expectedValue = randomLong.toString()
        val previousValue = randomListOf(minimumSize = 0) { randomLong.toString() }
        every { parserContext.text } returns expectedValue
        every { currentNode.parameters } returns previousValue

        // execution
        converter.exitParameterNumericValue(parserContext)

        // validation
        verify { currentNode.parameters = previousValue + expectedValue }
    }

    @Test
    fun `exitParameterBooleanLiteral should append the received value in the current node parameter list without the surrounding quotes`() {
        // scenario
        val parserContext = mockk<ParameterBooleanLiteralContext>()
        val expectedValue = randomBoolean.toString()
        val previousValue = randomListOf(minimumSize = 0) { randomLong.toString() }
        every { parserContext.text } returns expectedValue
        every { currentNode.parameters } returns previousValue

        // execution
        converter.exitParameterBooleanLiteral(parserContext)

        // validation
        verify { currentNode.parameters = previousValue + expectedValue }
    }
}
