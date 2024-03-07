package br.com.dillmann.dynamicquery.core.specification.path

import br.com.dillmann.dynamicquery.core.specification.exception.UnknownAttributeNameException
import javax.persistence.criteria.From
import javax.persistence.criteria.Path

/**
 * Utility class able to resolve a [Path] from any given starting point and full attribute name
 */
object PathResolver {

    /**
     * Given a full attribute path and a starting point, navigates the JPA metamodel tree and returns the path
     * node that matches the provided path
     *
     * @param attributeFullPath Full path of the attribute from the starting point where each level is separated by
     * a dot
     * @param startingPoint Root element of the search
     */
    @JvmStatic
    fun resolve(attributeFullPath: String, startingPoint: Path<out Any>): Path<out Any> =
        resolve(startingPoint, attributeFullPath.split('.').iterator(), attributeFullPath)

    private tailrec fun resolve(
        currentNode: Path<out Any>,
        remainingElements: Iterator<String>,
        fullPath: String,
    ): Path<out Any> {
        if (!remainingElements.hasNext())
            return currentNode

        val nextElement = remainingElements.next()
        val nextNode = getNextNode(currentNode, nextElement, fullPath)

        // if the model isn't null, the node resolved to a regular attribute
        if (nextNode.model != null)
            return resolve(nextNode, remainingElements, fullPath)

        // if the model is null, the node resolved to an intermediary attribute
        val joinExpression = (currentNode as From<out Any, out Any>).join<Any, Any>(nextElement)
        return resolve(joinExpression, remainingElements, fullPath)
    }

    private fun getNextNode(currentNode: Path<out Any>, attributeName: String, fullPath: String): Path<out Any> {
        try {
            return currentNode.get(attributeName)
        } catch (ex: IllegalArgumentException) {
            throw UnknownAttributeNameException(fullPath, ex)
        }
    }
}
