package br.com.dillmann.dynamicquery.core.grammar.converter

import br.com.dillmann.dynamicquery.core.grammar.dsl.DynamicQueryDslParser.*
import br.com.dillmann.dynamicquery.core.randomBoolean
import br.com.dillmann.dynamicquery.core.randomListOf
import br.com.dillmann.dynamicquery.core.randomLong
import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.specification.Specification
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

    private val specification = mockk<Specification>()
    private val currentNode = mockk<TreeNode>()
    private val rootNode = mockk<TreeNode>()
    private lateinit var converter: GrammarConverter

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkConstructor(GrammarConverterContext::class, TreeNodeCompiler::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { anyConstructed<TreeNodeCompiler>().compile() } returns specification
        every { anyConstructed<GrammarConverterContext>().startNode(any()) } just Runs
        every { anyConstructed<GrammarConverterContext>().endNode() } just Runs
        every { anyConstructed<GrammarConverterContext>().currentNode } returns currentNode
        every { anyConstructed<GrammarConverterContext>().root } returns rootNode
        every { currentNode.operation = any() } just Runs
        every { currentNode.attributeName = any() } just Runs
        every { currentNode.parameters = any() } just Runs
        every { currentNode.logicalOperator = any() } just Runs
        every { currentNode.parameters } returns emptyList()

        converter = GrammarConverter()
    }

    @Test
    fun `specification should compile the internal tree from the root node and return the result`() {
        // execution
        val result = converter.specification()

        // validation
        assertEquals(specification, result)
        verify { TreeNodeCompiler(rootNode).compile() }
    }

    @Test
    fun `enterGroup should start a new group node in the internal tree`() {
        // execution
        converter.enterGroup(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().startNode(TreeNodeType.GROUP) }
    }

    @Test
    fun `exitGroup should close the current node in the internal tree`() {
        // execution
        converter.exitGroup(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().endNode() }
    }

    @Test
    fun `enterExpression should start a new filter node in the internal tree`() {
        // execution
        converter.enterExpression(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().startNode(TreeNodeType.FILTER) }
    }

    @Test
    fun `exitExpression should close the current node in the internal tree`() {
        // execution
        converter.exitExpression(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().endNode() }
    }

    @Test
    fun `enterNegation should start a new negation node in the internal tree`() {
        // execution
        converter.enterNegation(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().startNode(TreeNodeType.NEGATION) }
    }

    @Test
    fun `exitNegation should close the current node in the internal tree`() {
        // execution
        converter.exitNegation(mockk())

        // validation
        verify { anyConstructed<GrammarConverterContext>().endNode() }
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
            anyConstructed<GrammarConverterContext>().startNode(TreeNodeType.LOGICAL_OPERATOR)
            currentNode.logicalOperator = expectedValue
            anyConstructed<GrammarConverterContext>().endNode()
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
