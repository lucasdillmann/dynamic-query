package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecificationFactory
import br.com.dillmann.dynamicquery.specification.group.GroupSpecification
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType
import br.com.dillmann.dynamicquery.specification.predicate.negation.NegationSpecification
import io.mockk.*
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

/**
 * [DynamicQueryCompiler] unit tests
 */
class DynamicQueryCompilerUnitTests {

    private val groupSpecification = mockk<GroupSpecification>()
    private val predicateSpecification = mockk<PredicateSpecification>()
    private val negationSpecification = mockk<NegationSpecification>()

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkObject(DynamicQuerySpecificationFactory)
            mockkStatic(DynamicQuerySpecificationFactory::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { DynamicQuerySpecificationFactory.negate(any()) } returns negationSpecification
        every { DynamicQuerySpecificationFactory.predicate(any(), any(), any()) } returns predicateSpecification
        every { DynamicQuerySpecificationFactory.group(any(), any(), any()) } returns groupSpecification
    }

    @Test
    fun `compile should produce the expected result when the tree contains a single predicate`() {
        // scenario
        val type = PredicateType.entries.random()
        val predicate = buildNode(
            type = TreeNodeType.PREDICATE,
            predicateType = type,
            parameters = listOf(randomString, randomString),
        )

        // execution
        val result = DynamicQueryCompiler.compile(predicate)

        // validation
        verify { DynamicQuerySpecificationFactory.predicate(type, predicate.attributeName!!, predicate.parameters!!) }
        assertEquals(predicateSpecification, result)
    }

    @Test
    fun `compile should produce the expected result when the tree contains a group`() {
        // scenario
        val group = buildNode(TreeNodeType.GROUP)
        val firstPredicate = buildNode(TreeNodeType.PREDICATE, predicateType = PredicateType.IS_NOT_NULL, parent = group)
        val secondPredicate = buildNode(TreeNodeType.PREDICATE, predicateType = PredicateType.IS_NULL, parent = group)
        val thirdPredicate = buildNode(TreeNodeType.PREDICATE, predicateType = PredicateType.IS_EMPTY, parent = group)
        val firstLogicalOperator = buildNode(TreeNodeType.LOGICAL_OPERATOR, logicalOperator = LogicalOperatorType.AND)
        val secondLogicalOperator = buildNode(TreeNodeType.LOGICAL_OPERATOR, logicalOperator = LogicalOperatorType.OR)
        val firstSpecification = mockk<PredicateSpecification>()
        val secondSpecification = mockk<PredicateSpecification>()
        val thirdSpecification = mockk<PredicateSpecification>()
        val firstGroup = mockk<GroupSpecification>()
        val secondGroup = mockk<GroupSpecification>()

        group.addChild(firstPredicate)
        group.addChild(firstLogicalOperator)
        group.addChild(secondPredicate)
        group.addChild(secondLogicalOperator)
        group.addChild(thirdPredicate)

        every { DynamicQuerySpecificationFactory.predicate(any(), firstPredicate.attributeName!!, any()) } returns firstSpecification
        every { DynamicQuerySpecificationFactory.predicate(any(), secondPredicate.attributeName!!, any()) } returns secondSpecification
        every { DynamicQuerySpecificationFactory.predicate(any(), thirdPredicate.attributeName!!, any()) } returns thirdSpecification
        every { DynamicQuerySpecificationFactory.group(any(), any(), any()) } returnsMany listOf(firstGroup, secondGroup)

        // execution
        val result = DynamicQueryCompiler.compile(group)

        // validation
        verify { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_NOT_NULL, firstPredicate.attributeName!!) }
        verify { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_NULL, secondPredicate.attributeName!!) }
        verify { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_EMPTY, thirdPredicate.attributeName!!) }
        verify { DynamicQuerySpecificationFactory.group(LogicalOperatorType.AND, firstSpecification, secondSpecification) }
        verify { DynamicQuerySpecificationFactory.group(LogicalOperatorType.OR, firstGroup, thirdSpecification) }
        assertEquals(secondGroup, result)
    }

    @Test
    fun `compile should produce the expected result when the tree contains a negation`() {
        // scenario
        val negation = buildNode(TreeNodeType.NEGATION)
        val predicate = buildNode(TreeNodeType.PREDICATE, predicateType = PredicateType.IS_NULL)
        negation.addChild(predicate)

        // execution
        val result = DynamicQueryCompiler.compile(negation)

        // validation
        verify { DynamicQuerySpecificationFactory.predicate(PredicateType.IS_NULL, predicate.attributeName!!) }
        verify { DynamicQuerySpecificationFactory.negate(predicateSpecification) }
        assertEquals(negationSpecification, result)
    }

    @Test
    fun `compile should throw an error when asked to compile a logical operator in the root node`() {
        // scenario
        val logicalOperator = buildNode(TreeNodeType.LOGICAL_OPERATOR, logicalOperator = LogicalOperatorType.AND)

        // validation
        assertThrows<IllegalStateException>("Logical operators should be compiled indirectly by groups") {
            DynamicQueryCompiler.compile(logicalOperator)
        }
    }

    private fun buildNode(
        type: TreeNodeType,
        parent: TreeNode? = null,
        predicateType: PredicateType? = null,
        parameters: List<String> = emptyList(),
        logicalOperator: LogicalOperatorType? = null,
    ) = TreeNode(type, parent).also {
        it.parameters = parameters
        it.attributeName = randomString
        it.logicalOperator = logicalOperator?.identifier
        it.operation = predicateType?.identifier
    }
}
