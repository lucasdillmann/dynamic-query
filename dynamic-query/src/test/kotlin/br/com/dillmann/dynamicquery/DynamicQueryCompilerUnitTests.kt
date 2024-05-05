package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameter
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeParameterType
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecificationFactory
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.parameter.ParameterFactory
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

/**
 * [DynamicQueryCompiler] unit tests
 */
class DynamicQueryCompilerUnitTests {

    private val specification = mockk<DynamicQuerySpecification>()
    private val parameter = mockk<Parameter>()

    @BeforeEach
    fun setUp() {
        mockkObject(DynamicQuerySpecificationFactory, ParameterFactory)
        mockkStatic(DynamicQuerySpecificationFactory::class, ParameterFactory::class)

        every { DynamicQuerySpecificationFactory.negate(any()) } returns specification
        every { DynamicQuerySpecificationFactory.group(any(), any(), any()) } returns specification
        every { DynamicQuerySpecificationFactory.predicate(any(), any()) } returns specification
        every { ParameterFactory.literal(any()) } returns parameter
        every { ParameterFactory.reference(any()) } returns parameter
        every { ParameterFactory.operation(any(), any()) } returns parameter
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `compile should be able to produce a DynamicQuerySpecification for a simple predicate`() {
        // scenario
        val type = PredicateType.entries.random()

        val rootNode = TreeNode(TreeNodeType.PREDICATE, null)
        rootNode.identifier = type.identifier

        val childNode = TreeNode(TreeNodeType.PARAMETER_LITERAL, rootNode)
        childNode.parameter = TreeNodeParameter(TreeNodeParameterType.ATTRIBUTE_NAME, randomString)
        rootNode.addChild(childNode)

        // execution
        val output = DynamicQueryCompiler.compile(rootNode)

        // validation
        assertEquals(specification, output)
        verify { ParameterFactory.reference(childNode.parameter!!.value!!) }
        verify { DynamicQuerySpecificationFactory.predicate(type, listOf(parameter)) }
    }

    @Test
    fun `compile should be able to produce a DynamicQuerySpecification for a group of predicates`() {
        // scenario
        val groupSpecification = mockk<DynamicQuerySpecification>()
        val leftOperationSpecification = mockk<DynamicQuerySpecification>()
        val rightOperationSpecification = mockk<DynamicQuerySpecification>()
        every { DynamicQuerySpecificationFactory.group(any(), any(), any()) } returns groupSpecification
        every { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_NOT_NULL, any()) } returns leftOperationSpecification
        every { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_NULL, any()) } returns rightOperationSpecification

        val rootNode = TreeNode(TreeNodeType.GROUP, null)

        val leftOperation = TreeNode(TreeNodeType.PREDICATE, rootNode)
        leftOperation.identifier = PredicateType.IS_NOT_NULL.identifier
        rootNode.addChild(leftOperation)

        val logicalOperatorNode = TreeNode(TreeNodeType.LOGICAL_OPERATOR, rootNode)
        val logicalOperator = LogicalOperatorType.entries.random()
        logicalOperatorNode.logicalOperator = logicalOperator.identifier
        rootNode.addChild(logicalOperatorNode)

        val rightOperation = TreeNode(TreeNodeType.PREDICATE, rootNode)
        rightOperation.identifier = PredicateType.IS_NULL.identifier
        rootNode.addChild(rightOperation)

        // execution
        val output = DynamicQueryCompiler.compile(rootNode)

        // validation
        assertEquals(groupSpecification, output)
        verify { DynamicQuerySpecificationFactory.group(logicalOperator, leftOperationSpecification, rightOperationSpecification) }
    }

    @Test
    fun `compile should be able to produce a DynamicQuerySpecification for a negation`() {
        // scenario
        val negationSpecification = mockk<DynamicQuerySpecification>()
        every { DynamicQuerySpecificationFactory.negate(any()) } returns negationSpecification

        val type = PredicateType.entries.random()

        val rootNode = TreeNode(TreeNodeType.NEGATION, null)

        val secondLevelNode = TreeNode(TreeNodeType.PREDICATE, null)
        secondLevelNode.identifier = type.identifier
        rootNode.addChild(secondLevelNode)

        val thirdLevelNode = TreeNode(TreeNodeType.PARAMETER_LITERAL, secondLevelNode)
        thirdLevelNode.parameter = TreeNodeParameter(TreeNodeParameterType.LITERAL, randomString)
        secondLevelNode.addChild(thirdLevelNode)

        // execution
        val output = DynamicQueryCompiler.compile(rootNode)

        // validation
        assertEquals(negationSpecification, output)
        verify { DynamicQuerySpecificationFactory.negate(specification) }
    }

    @Test
    fun `compile should be able to produce a DynamicQuerySpecification for a predicate with child operations`() {
        // scenario
        val referenceParameter = mockk<Parameter>()
        every { ParameterFactory.reference(any()) } returns referenceParameter

        val rootNode = TreeNode(TreeNodeType.PREDICATE, null)
        rootNode.identifier = PredicateType.IS_NOT_EMPTY.identifier

        val operationNode = TreeNode(TreeNodeType.OPERATION, rootNode)
        operationNode.identifier = OperationParameterType.TRIM.identifier
        rootNode.addChild(operationNode)

        val attributeNode = TreeNode(TreeNodeType.PARAMETER_LITERAL, operationNode)
        attributeNode.parameter = TreeNodeParameter(TreeNodeParameterType.ATTRIBUTE_NAME, randomString)
        operationNode.addChild(attributeNode)

        // execution
        val output = DynamicQueryCompiler.compile(rootNode)

        // validation
        assertEquals(specification, output)
        verify { ParameterFactory.reference(attributeNode.parameter!!.value!!) }
        verify { ParameterFactory.operation(OperationParameterType.TRIM, listOf(referenceParameter)) }
    }

    @Test
    fun `compile should throw an error when the root node is a logical operator`() {
        // scenario
        val input = TreeNode(TreeNodeType.LOGICAL_OPERATOR, null)

        // validation
        assertThrows<IllegalStateException>("Logical operators should be compiled indirectly by predicates") {

            // execution
            DynamicQueryCompiler.compile(input)
        }
    }

    @Test
    fun `compile should throw an error when the root node is a operation`() {
        // scenario
        val input = TreeNode(TreeNodeType.OPERATION, null)

        // validation
        assertThrows<IllegalStateException>("Operation operators should be compiled indirectly by predicates") {

            // execution
            DynamicQueryCompiler.compile(input)
        }
    }

    @Test
    fun `compile should throw an error when the root node is a literal parameter`() {
        // scenario
        val input = TreeNode(TreeNodeType.PARAMETER_LITERAL, null)

        // validation
        assertThrows<IllegalStateException>("Literal parameters should be compiled indirectly by predicates") {

            // execution
            DynamicQueryCompiler.compile(input)
        }
    }

}
