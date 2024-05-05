package br.com.dillmann.dynamicquery.specification.parameter.literal

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root

/**
 * [Parameter] implementation for literal values
 *
 * @param value Nullable [String] representation of the value
 */
internal class LiteralParameter(private val value: String?): Parameter {

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
        if (value == null)
            return builder.nullLiteral(Any::class.java) as Expression<T>

        val targetValue: Any =
            if (targetType == null) value
            else ValueParsers.parse(value, targetType)

        return builder.literal(targetValue) as Expression<T>
    }
}
