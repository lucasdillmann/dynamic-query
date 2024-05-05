package br.com.dillmann.dynamicquery.specification.parameter.reference

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.path.PathConverters
import br.com.dillmann.dynamicquery.specification.path.PathResolver
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root

/**
 * [Parameter] implementation for references (such as field names)
 *
 * @param attributeName Name of the attribute being referenced
 */
internal class ReferenceParameter(private val attributeName: String) : Parameter {

    /**
     * Produces a JPA [Expression] from the current parameter
     *
     * @param T Generic type of the expression
     * @param root Root of the JPA query (the main entity being queried)
     * @param query Current JPA query
     * @param builder JPA expression builder
     * @param targetType Target type of the expression. Always ignored.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T: Any> asExpression(
        root: Root<*>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder,
        targetType: Class<T>?,
    ): Expression<T> {
        val path = PathConverters.convert(attributeName, root)
        return PathResolver.resolve(path, root) as Expression<T>
    }
}
