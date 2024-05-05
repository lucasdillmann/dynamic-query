package br.com.dillmann.dynamicquery.specification.parameter.operation

import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType.*
import br.com.dillmann.dynamicquery.specification.randomListOf
import br.com.dillmann.dynamicquery.specification.validation.ArgumentCountChecker
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertEquals

/**
 * [OperationExpressionFactory] unit tests
 */
class OperationExpressionFactoryUnitTests {

    private val builder = mockk<CriteriaBuilder>(relaxed = true)

    @BeforeEach
    fun setUp() {
        mockkObject(ArgumentCountChecker)
    }

    @ParameterizedTest
    @EnumSource(value = OperationParameterType::class)
    fun `buildExpression should check if the amount of input parameters is within the expected bounds`(
        type: OperationParameterType,
    ) {
        // scenario
        val range = type.argumentCountRange
        val parameters = randomListOf(minimumSize = range.first, maximumSize = range.last) {
            mockk<Expression<out Any>>()
          }

        // execution
        OperationExpressionFactory.buildExpression(builder, type, parameters)

        // validation
        verify { ArgumentCountChecker.check(type.identifier, type.argumentCountRange, parameters.size) }
    }

    @ParameterizedTest
    @EnumSource(value = OperationParameterType::class)
    fun `buildExpression should build and return the expected expression`(type: OperationParameterType) {
        // scenario
        val parameterSizeRange = type.argumentCountRange
        val parameters = randomListOf(minimumSize = parameterSizeRange.first, maximumSize = parameterSizeRange.last) {
            mockk<Expression<out Any>>()
        }
        val (expectedOutput, callVerifier) = configureExpectedExpression(type, parameters)

        // execution
        val output = OperationExpressionFactory.buildExpression(builder, type, parameters)

        // validation
        assertEquals(expectedOutput, output)
        callVerifier(parameters)
    }

    @Suppress("UNCHECKED_CAST")
    private fun configureExpectedExpression(
        type: OperationParameterType,
        parameters: List<Expression<out Any>>
    ): Pair<Expression<Any>, (List<Expression<out Any>>) -> Unit> {
        val expression = mockk<Expression<Any>>()
        val callVerifier: (List<Expression<out Any>>) -> Unit = when (type) {
            ABS -> {
                every { builder.abs(any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.abs(parameters.first() as Expression<Number>) } }
            }

            AVG -> {
                every { builder.avg(any<Expression<Number>>()) } returns expression as Expression<Double>
                { verify { builder.avg(parameters.first() as Expression<Number>) } }
            }

            CEILING -> {
                every { builder.ceiling(any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.ceiling(parameters.first() as Expression<Number>) } }
            }

            COALESCE -> {
                every { builder.coalesce(any<Expression<Any>>(), any()) } returns expression
                { verify { builder.coalesce(parameters.first(), parameters.last()) } }
            }

            CONCAT -> {
                every { builder.concat(any<Expression<String>>(), any<Expression<String>>()) } returns expression as Expression<String>
                { verify { builder.concat(parameters.first() as Expression<String>, parameters.last() as Expression<String>) } }
            }

            COUNT -> {
                every { builder.count(any()) } returns expression as Expression<Long>
                { verify { builder.count(parameters.first()) } }
            }

            COUNT_DISTINCT -> {
                every { builder.countDistinct(any()) } returns expression as Expression<Long>
                { verify { builder.countDistinct(parameters.first()) } }
            }

            CURRENT_DATE -> {
                every { builder.currentDate() } returns expression as Expression<Date>
                { verify { builder.currentDate() } }
            }

            CURRENT_TIME -> {
                every { builder.currentTime() } returns expression as Expression<Time>
                { verify { builder.currentTime() } }
            }

            CURRENT_TIMESTAMP -> {
                every { builder.currentTimestamp() } returns expression as Expression<Timestamp>
                { verify { builder.currentTimestamp() } }
            }

            DIFF -> {
                every { builder.diff(any<Expression<Number>>(), any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.diff(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>) } }
            }

            EXP -> {
                every { builder.exp(any<Expression<Number>>()) } returns expression as Expression<Double>
                { verify { builder.exp(parameters.first() as Expression<Number>) } }
            }

            FLOOR -> {
                every { builder.floor(any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.floor(parameters.first() as Expression<Number>) } }
            }

            LENGTH -> {
                every { builder.length(any()) } returns expression as Expression<Int>
                { verify { builder.length(parameters.first() as Expression<String>) } }
            }

            LOCAL_DATE -> {
                every { builder.localDate() } returns expression as Expression<LocalDate>
                { verify { builder.localDate() } }
            }

            LOCAL_TIME -> {
                every { builder.localTime() } returns expression as Expression<LocalTime>
                { verify { builder.localTime() } }
            }

            LOCAL_DATE_TIME -> {
                every { builder.localDateTime() } returns expression as Expression<LocalDateTime>
                { verify { builder.localDateTime() } }
            }

            LOCATE -> {
                every { builder.locate(any<Expression<String>>(), any<Expression<String>>()) } returns expression as Expression<Int>
                { verify { builder.locate(parameters.first() as Expression<String>, parameters.last() as Expression<String>) } }
            }

            LOWER -> {
                every { builder.lower(any()) } returns expression as Expression<String>
                { verify { builder.lower(parameters.first() as Expression<String>) } }
            }

            MAX -> {
                every { builder.max(any<Expression<Long>>()) } returns expression as Expression<Long>
                { verify { builder.max(parameters.first() as Expression<Number>) } }
            }

            MIN -> {
                every { builder.min(any<Expression<Long>>()) } returns expression as Expression<Long>
                { verify { builder.min(parameters.first() as Expression<Number>) } }
            }

            MOD -> {
                every { builder.mod(any<Expression<Int>>(), any<Expression<Int>>()) } returns expression as Expression<Int>
                { verify { builder.mod(parameters.first() as Expression<Int>, parameters.last() as Expression<Int>) } }
            }

            NEG -> {
                every { builder.neg(any<Expression<Int>>()) } returns expression as Expression<Int>
                { verify { builder.neg(parameters.first() as Expression<Int>) } }
            }

            POWER -> {
                every { builder.power(any<Expression<Number>>(), any<Expression<Number>>()) } returns expression as Expression<Double>
                { verify { builder.power(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>) } }
            }

            PROD -> {
                every { builder.prod(any<Expression<Number>>(), any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.prod(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>) } }
            }

            QUOT -> {
                every { builder.quot(any<Expression<Number>>(), any<Expression<Number>>()) } returns expression as Expression<Number>
                { verify { builder.quot(parameters.first() as Expression<Number>, parameters.last() as Expression<Number>) } }
            }

            SIZE -> {
                every { builder.size(any<Expression<Collection<Any>>>()) } returns expression as Expression<Int>
                { verify { builder.size(parameters.first() as Expression<Collection<Any>>) } }
            }

            SQRT -> {
                every { builder.sqrt(any<Expression<Number>>()) } returns expression as Expression<Double>
                { verify { builder.sqrt(parameters.first() as Expression<Number>) } }
            }

            SUBSTRING -> {
                every { builder.substring(any<Expression<String>>(), any<Expression<Int>>()) } returns expression as Expression<String>
                every { builder.substring(any<Expression<String>>(), any<Expression<Int>>(), any<Expression<Int>>()) } returns expression as Expression<String>

                {
                    val firstParameter = parameters.first() as Expression<String>
                    val lastParameter = parameters.last() as Expression<Int>

                    if (parameters.size == 2) verify { builder.substring(firstParameter, lastParameter) }
                    else verify { builder.substring(firstParameter, parameters[1] as Expression<Int>, lastParameter) }
                }
            }

            SUM -> {
                every { builder.sum(any<Expression<Number>>()) } returns expression as Expression<Number>
                every { builder.sum(any<Expression<Number>>(), any<Expression<Number>>()) } returns expression as Expression<Number>

                {
                    val firstParameter = parameters.first() as Expression<Number>
                    if (parameters.size == 1) verify { builder.sum(firstParameter) }
                    else verify { builder.sum(firstParameter, parameters.last() as Expression<Number>) }
                }
            }

            TO_BIG_DECIMAL -> {
                every { builder.toBigDecimal(any<Expression<Number>>()) } returns expression as Expression<BigDecimal>
                { verify { builder.toBigDecimal(parameters.first() as Expression<Number>) } }
            }

            TO_BIG_INTEGER -> {
                every { builder.toBigInteger(any<Expression<Number>>()) } returns expression as Expression<BigInteger>
                { verify { builder.toBigInteger(parameters.first() as Expression<Number>) } }
            }

            TO_DOUBLE -> {
                every { builder.toDouble(any<Expression<Number>>()) } returns expression as Expression<Double>
                { verify { builder.toDouble(parameters.first() as Expression<Number>) } }
            }

            TO_FLOAT -> {
                every { builder.toFloat(any<Expression<Number>>()) } returns expression as Expression<Float>
                { verify { builder.toFloat(parameters.first() as Expression<Number>) } }
            }

            TO_INTEGER -> {
                every { builder.toInteger(any<Expression<Number>>()) } returns expression as Expression<Int>
                { verify { builder.toInteger(parameters.first() as Expression<Number>) } }
            }

            TO_LONG -> {
                every { builder.toLong(any<Expression<Number>>()) } returns expression as Expression<Long>
                { verify { builder.toLong(parameters.first() as Expression<Number>) } }
            }

            TRIM -> {
                every { builder.trim(any<Expression<String>>()) } returns expression as Expression<String>
                { verify { builder.trim(parameters.first() as Expression<String>) } }
            }

            UPPER -> {
                every { builder.upper(any()) } returns expression as Expression<String>
                { verify { builder.upper(parameters.first() as Expression<String>) } }
            }
        }

        return expression to callVerifier
    }
}
