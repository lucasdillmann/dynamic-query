package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * [GrammarConversionContext] unit tests
 */
class GrammarConversionContextUnitTests {

    private val context = GrammarConversionContext()

    @Test
    fun `initial state should point currentNode to the root node`() {
        assertEquals(context.root, context.currentNode)
    }

    @Test
    fun `startNode should start a new child node of the current node and set it as the new current node`() {
        // sccenario
        val type = TreeNodeType.entries.random()

        // execution
        context.startNode(type)

        // validation
        assertNotEquals(context.root, context.currentNode)
        assertEquals(type, context.currentNode.type)
        assertEquals(context.root, context.currentNode.parent)
        assertTrue { context.currentNode in context.root.children }
    }

    @Test
    fun `endNode should close the current node and set its parent as the new current node`() {
        // scenario
        context.startNode(TreeNodeType.GROUP)
        val expectedResult = context.currentNode

        context.startNode(TreeNodeType.PREDICATE)

        // execution
        context.endNode()

        // validation
        assertEquals(expectedResult, context.currentNode)
    }

    @Test
    fun `endNode should throw an error when asked to close the root node`() {
        // validation
        assertThrows<IllegalStateException>("Cannot close the root node") {
            // execution
            context.endNode()
        }
    }
}
