package br.com.dillmann.dynamicquery.grammar.tree

import java.util.*

/**
 * Conversion tree node definition
 *
 * This class stores the metadata read using ANTLR parser during the conversion. When parse is completed,
 * the metadata stored here can be used to produce a predicate.
 *
 * @property type Type of the node
 * @property parent Parent node (optional)
 */
class TreeNode(var type: TreeNodeType, val parent: TreeNode?) {

    /**
     * Relation of child nodes
     */
    val children: MutableList<TreeNode> = LinkedList<TreeNode>()

    /**
     * Target operation of the node (equals, between and alike) when the node is either
     * [TreeNodeType.PREDICATE_OPERATION] or [TreeNodeType.TRANSFORMATION_OPERATION]
     */
    var operation: String? = null

    /**
     * The operator when node is a [TreeNodeType.LOGICAL_OPERATOR]
     */
    var logicalOperator: String? = null

    /**
     * The type of the parameter when the node is a [TreeNodeType.PARAMETER_LITERAL]
     */
    var parameter: TreeNodeParameter? = null

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
