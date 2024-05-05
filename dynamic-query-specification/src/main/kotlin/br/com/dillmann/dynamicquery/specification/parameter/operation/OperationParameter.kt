package br.com.dillmann.dynamicquery.specification.parameter.operation

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root

/**
 * [Parameter] implementation for operation parameters (such as lower, trim, upper and alike)
 *
 * @param type Type of the operation
 * @param parameters Inner arguments of the operation
 */
internal class OperationParameter(
    private val type: OperationParameterType,
    private val parameters: List<Parameter>,
): Parameter {

    /**
     * Produces a JPA [Expression] from the current parameter with a specific type
     *
     * @param T Generic type of the expression
     * @param root Root of the JPA query (the main entity being queried)
     * @param query Current JPA query
     * @param builder JPA expression builder
     * @param targetType Target type of the expression. When informed, value will be parsed to such type before being
     * returned.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T: Any> asExpression(
        root: Root<*>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder,
        targetType: Class<T>?,
    ): Expression<T> {
        val evaluatedParameters = parameters.map { it.asExpression(root, query, builder, targetType) }
        return OperationExpressionFactory.buildExpression(builder, type, evaluatedParameters) as Expression<T>
    }
}
