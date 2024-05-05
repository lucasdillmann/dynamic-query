package br.com.dillmann.dynamicquery.specification.parameter.operation

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.randomListOf
import io.mockk.*
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [OperationParameter] unit tests
 */
class OperationParameterUnitTests {

    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val expression = mockk<Expression<Any>>()

    @BeforeEach
    fun setUp() {
        mockkObject(OperationExpressionFactory)
        every { OperationExpressionFactory.buildExpression(any(), any(), any()) } returns expression
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `asExpression should evaluate every given parameter, build a expression from them and return it`() {
        // scenario
        val type = OperationParameterType.entries.random()
        val parameterExpressions = randomListOf { mockk<Expression<Any>>() }
        val parameters = parameterExpressions.map {
            mockk<Parameter> {
                every { asExpression<Any>(any(), any(), any(), any()) } returns it
            }
        }

        // execution
        val output = OperationParameter(type, parameters).asExpression<Any>(root, query, builder, null)

        // validation
        assertEquals(expression, output)
        verify { OperationExpressionFactory.buildExpression(builder, type, parameterExpressions) }
        parameters.forEach {
            verify { it.asExpression<Any>(root, query, builder, null) }
        }
    }
}
