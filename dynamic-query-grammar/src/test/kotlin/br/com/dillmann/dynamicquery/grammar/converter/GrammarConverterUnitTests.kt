package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser.*
import br.com.dillmann.dynamicquery.grammar.randomBoolean
import br.com.dillmann.dynamicquery.grammar.randomLong
import br.com.dillmann.dynamicquery.grammar.randomString
import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameter
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameterType
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
        every { currentNode.identifier = any() } just Runs
        every { currentNode.logicalOperator = any() } just Runs
        every { currentNode.parameter = any() } just Runs

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
    fun `enterOperation should start a new filter node in the internal tree`() {
        // execution
        converter.enterOperation(mockk())

        // validation
        verify { anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.OPERATION) }
    }

    @Test
    fun `exitOperation should close the current node in the internal tree`() {
        // execution
        converter.exitOperation(mockk())

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
    fun `exitAttributeName should include the received attribute name in the current tree node as a parameter`() {
        // scenario
        val parserContext = mockk<AttributeNameContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitAttributeName(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PARAMETER_LITERAL)
            currentNode.parameter = TreeNodeParameter(TreeNodeParameterType.ATTRIBUTE_NAME, expectedValue)
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }

    @Test
    fun `exitPredicateType should set the received operation name in the current tree node`() {
        // scenario
        val parserContext = mockk<PredicateTypeContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitPredicateType(parserContext)

        // validation
        verify { currentNode.identifier = expectedValue }
    }

    @Test
    fun `exitOperationType should set the received operation name in the current tree node`() {
        // scenario
        val parserContext = mockk<OperationTypeContext>()
        val expectedValue = randomString
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitOperationType(parserContext)

        // validation
        verify { currentNode.identifier = expectedValue }
    }

    @Test
    fun `exitStringLiteral should append the received value without the surrounding quotes as a parameter in the current node`() {
        // scenario
        val parserContext = mockk<StringLiteralContext>()
        val expectedValue = randomString
        every { parserContext.text } returns "\"$expectedValue\""

        // execution
        converter.exitStringLiteral(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PARAMETER_LITERAL)
            currentNode.parameter = TreeNodeParameter(TreeNodeParameterType.LITERAL, expectedValue)
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }

    @Test
    fun `exitNumericLiteral should be able to parse the received value as a Number and store it as a parameter of the current node`() {
        // scenario
        val parserContext = mockk<NumericLiteralContext>()
        val expectedValue = randomLong.toString()
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitNumericLiteral(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PARAMETER_LITERAL)
            currentNode.parameter = TreeNodeParameter(TreeNodeParameterType.LITERAL, expectedValue)
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }

    @Test
    fun `exitNumericLiteral should be able to parse the received value as a Boolean and store it as a parameter of the current node`() {
        // scenario
        val parserContext = mockk<BooleanLiteralContext>()
        val expectedValue = randomBoolean.toString()
        every { parserContext.text } returns expectedValue

        // execution
        converter.exitBooleanLiteral(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PARAMETER_LITERAL)
            currentNode.parameter = TreeNodeParameter(TreeNodeParameterType.LITERAL, expectedValue)
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }

    @Test
    fun `exitNumericLiteral should be able to store a null as a parameter of the current node`() {
        // scenario
        val parserContext = mockk<NullLiteralContext>()

        // execution
        converter.exitNullLiteral(parserContext)

        // validation
        verifyOrder {
            anyConstructed<GrammarConversionContext>().startNode(TreeNodeType.PARAMETER_LITERAL)
            currentNode.parameter = TreeNodeParameter(TreeNodeParameterType.LITERAL, null)
            anyConstructed<GrammarConversionContext>().endNode()
        }
    }
}
