package br.com.dillmann.dynamicquery.specification.parameter.operation

import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType.*
import br.com.dillmann.dynamicquery.specification.validation.ArgumentCountChecker
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression

/**
 * [Expression] factory for data operations
 */
internal object OperationExpressionFactory {

    /**
     * Produces a JPA [Expression] for the requested [OperationParameterType] using given list of [Expression] as
     * the arguments.
     *
     * @param builder JPA's Criteria Builder instance to be used
     * @param type Type of the desired expression
     * @param parameters Parameters to be used in the expression construction
     */
    fun buildExpression(
        builder: CriteriaBuilder,
        type: OperationParameterType,
        parameters: List<Expression<out Any>>
    ): Expression<out Any> {
        ArgumentCountChecker.check(type.identifier, type.argumentCountRange, parameters.size)

        @Suppress("UNCHECKED_CAST")
        return when (type) {
            ABS -> builder.abs(parameters.first() as Expression<Number>)
            AVG -> builder.avg(parameters.first() as Expression<Number>)
            CEILING -> builder.ceiling(parameters.first() as Expression<Number>)
            COALESCE -> builder.coalesce(parameters.first(), parameters.last())
            CONCAT -> builder.concat(parameters.first() as Expression<String>, parameters.last() as Expression<String>)
            COUNT -> builder.count(parameters.first())
            COUNT_DISTINCT -> builder.countDistinct(parameters.first())
            CURRENT_DATE -> builder.currentDate()
            CURRENT_TIME -> builder.currentTime()
            CURRENT_TIMESTAMP -> builder.currentTimestamp()
            DIFF -> builder.diff(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>)
            EXP -> builder.exp(parameters.first() as Expression<Number>)
            FLOOR -> builder.floor(parameters.first() as Expression<Number>)
            LENGTH -> builder.length(parameters.first() as Expression<String>)
            LOCAL_DATE -> builder.localDate()
            LOCAL_TIME -> builder.localTime()
            LOCAL_DATE_TIME -> builder.localDateTime()
            LOCATE -> builder.locate(parameters.first() as Expression<String>, parameters.last() as Expression<String>)
            LOWER -> builder.lower(parameters.first() as Expression<String>)
            MAX -> builder.max(parameters.first() as Expression<Number>)
            MIN -> builder.min(parameters.first() as Expression<Number>)
            MOD -> builder.mod(parameters.first() as Expression<Int>, parameters.last() as Expression<Int>)
            NEG -> builder.neg(parameters.first() as Expression<Int>)
            POWER -> builder.power(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>)
            PROD -> builder.prod(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>)
            QUOT -> builder.quot(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>)
            SIZE -> builder.size(parameters.first() as Expression<Collection<Any>>)
            SQRT -> builder.sqrt(parameters.first() as Expression<Number>)
            SUBSTRING -> buildSubstringExpression(builder, parameters)
            SUM -> buildSumExpression(builder, parameters)
            TO_BIG_DECIMAL -> builder.toBigDecimal(parameters.first() as Expression<Number>)
            TO_BIG_INTEGER -> builder.toBigInteger(parameters.first() as Expression<Number>)
            TO_DOUBLE -> builder.toDouble(parameters.first() as Expression<Number>)
            TO_FLOAT -> builder.toFloat(parameters.first() as Expression<Number>)
            TO_INTEGER -> builder.toInteger(parameters.first() as Expression<Number>)
            TO_LONG -> builder.toLong(parameters.first() as Expression<Number>)
            TRIM -> builder.trim(parameters.first() as Expression<String>)
            UPPER -> builder.upper(parameters.first() as Expression<String>)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun buildSubstringExpression(
        builder: CriteriaBuilder,
        parameters: List<Expression<out Any>>,
    ): Expression<String> {
        val firstParameter = parameters.first() as Expression<String>
        val lastParameter = parameters.last() as Expression<Int>

        return if (parameters.size == 2) builder.substring(firstParameter, lastParameter)
        else builder.substring(firstParameter, parameters[1] as Expression<Int>, lastParameter)
    }

    @Suppress("UNCHECKED_CAST")
    private fun buildSumExpression(
        builder: CriteriaBuilder,
        parameters: List<Expression<out Any>>,
    ): Expression<Number> {
        val firstParameter = parameters.first() as Expression<Number>

        return if (parameters.size == 1) builder.sum(firstParameter)
        else builder.sum(firstParameter, parameters.last() as Expression<Number>)
    }
}
