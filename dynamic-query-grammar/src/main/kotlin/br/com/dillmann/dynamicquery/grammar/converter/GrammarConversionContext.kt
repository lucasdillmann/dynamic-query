package br.com.dillmann.dynamicquery.grammar.converter

import br.com.dillmann.dynamicquery.grammar.tree.TreeNode
import br.com.dillmann.dynamicquery.grammar.tree.TreeNodeType

/**
 * Predicate tree holder for the [GrammarConverter]
 *
 * This class stores the predicate tree while the DSL is being converted to a JPA-based specification
 */
class GrammarConversionContext {

    /**
     * Root of the tree
     */
    val root = TreeNode(TreeNodeType.GROUP, null)

    /**
     * Currently open/active node
     */
    var currentNode = root
        private set

    /**
     * Starts a new node and inserts it in the tree, also marking it as the [currentNode]
     *
     * @param type Type of the new node
     */
    fun startNode(type: TreeNodeType) {
        val newNode = TreeNode(type, currentNode)
        currentNode.addChild(newNode)
        currentNode = newNode
    }

    /**
     * Closes the currently active/open node, replacing the [currentNode] value with its parent node
     *
     * @throws IllegalStateException when called for the root node
     */
    fun endNode() {
        check(currentNode.parent != null) { "Cannot close the root node" }
        currentNode = currentNode.parent!!
    }
}
