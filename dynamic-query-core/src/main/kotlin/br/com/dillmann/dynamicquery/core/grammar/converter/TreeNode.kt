package br.com.dillmann.dynamicquery.core.grammar.converter

/**
 * Conversion tree node definition
 *
 * This class stores the metadata read using ANTLR parser during the conversion. When parse is completed,
 * the metadata stored here can be used to produce a predicate.
 */
class TreeNode(var type: TreeNodeType, val parent: TreeNode?) {

    /**
     * Relation of child nodes
     */
    val children = mutableListOf<TreeNode>()

    /**
     * Target operation of the node (equals, between and alike) when the node is a [TreeNodeType.FILTER]
     */
    var operation: String? = null

    /**
     * Target name of the target attribute when the node is a [TreeNodeType.FILTER]
     */
    var attributeName: String? = null

    /**
     * Relation of arguments (like the start and end of a between operation) when the node is a [TreeNodeType.FILTER]
     */
    var parameters: List<String>? = null

    /**
     * The operator when node is a [TreeNodeType.LOGICAL_OPERATOR]
     */
    var logicalOperator: String? = null

    /**
     * Stores the provided [node] as a child of this node
     *
     * @throws IllegalArgumentException when current node type is not allowed to have children
     */
    fun addChild(node: TreeNode) {
        require(type.allowsChildren) { "Nodes of type $type cannot have children" }
        children.add(node)
    }
}
