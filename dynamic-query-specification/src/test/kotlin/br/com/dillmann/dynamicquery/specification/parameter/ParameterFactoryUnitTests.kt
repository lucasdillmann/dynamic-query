package br.com.dillmann.dynamicquery.specification.parameter

import br.com.dillmann.dynamicquery.specification.parameter.literal.LiteralParameter
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameter
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType
import br.com.dillmann.dynamicquery.specification.parameter.reference.ReferenceParameter
import br.com.dillmann.dynamicquery.specification.randomListOf
import br.com.dillmann.dynamicquery.specification.randomString
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * [ParameterFactory] unit tests
 */
class ParameterFactoryUnitTests {

    @Test
    fun `literal should produce and return a LiteralExpression`() {
        // execution
        val output = ParameterFactory.literal(randomString)

        // validation
        assertTrue { output is LiteralParameter }
    }

    @Test
    fun `reference should produce and return a LiteralExpression`() {
        // execution
        val output = ParameterFactory.reference(randomString)

        // validation
        assertTrue { output is ReferenceParameter }
    }

    @Test
    fun `operation should produce and return a OperationParameter`() {
        // scenario
        val parameters = randomListOf { mockk<Parameter>() }
        val type = OperationParameterType.entries.random()

        // execution
        val output = ParameterFactory.operation(type, parameters)

        // validation
        assertTrue { output is OperationParameter }
    }

}
