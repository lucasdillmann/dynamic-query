package br.com.dillmann.dynamicquery.grammar.tree

import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

/**
 * [TreeNode] unit tests
 */
class TreeNodeUnitTests {

    private val node = TreeNode(TreeNodeType.GROUP, null)

    @Test
    fun `addChild should store the given node as a child node`() {
        // scenario
        val child = mockk<TreeNode>()

        // execution
        node.addChild(child)

        // validation
        assertTrue { child in node.children }
    }

    @Test
    fun `addChild should throw an error the current node doesn't allow children`() {
        // scenario
        val type = TreeNodeType.entries.filterNot { it.allowsChildren }.random()
        val child = mockk<TreeNode>()

        // validation
        assertThrows<IllegalArgumentException>("Nodes of type $type cannot have children") {
            // execution
            TreeNode(type, null).addChild(child)
        }
    }

}
