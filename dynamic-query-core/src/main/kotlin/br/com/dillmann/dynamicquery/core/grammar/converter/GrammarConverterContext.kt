package br.com.dillmann.dynamicquery.core.grammar.converter

/**
 * Predicate tree holder for the [GrammarConverter]
 *
 * This class stores the predicate tree while the DSL is being converted to a JPA-based specification
 */
class GrammarConverterContext {

    /**
     * Root of the tree
     */
    val root = TreeNode(TreeNodeType.ROOT, null)

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
        require(type != TreeNodeType.ROOT) { "Cannot create child nodes of type ROOT" }
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
        check(currentNode.parent != null) { "Cannot close root node" }
        currentNode = currentNode.parent!!
    }
}
