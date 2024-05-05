package br.com.dillmann.dynamicquery.specification.parameter

import br.com.dillmann.dynamicquery.specification.parameter.literal.LiteralParameter
import br.com.dillmann.dynamicquery.specification.parameter.reference.ReferenceParameter
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameter
import br.com.dillmann.dynamicquery.specification.parameter.operation.OperationParameterType

/**
 * [Parameter] factory
 */
object ParameterFactory {

    /**
     * Produces a [Parameter] for literal values of any type
     *
     * @param value String representation of the value
     */
    fun literal(value: String?): Parameter =
        LiteralParameter(value)

    /**
     * Produces a [Parameter] for a field/attribute reference
     *
     * @param attributeName Name of the attribute
     */
    fun reference(attributeName: String): Parameter =
        ReferenceParameter(attributeName)

    /**
     * Produces a [Parameter] for an operation
     *
     * @param type Type of the operation
     * @param parameters Parameters for the operation
     */
    fun operation(type: OperationParameterType, parameters: List<Parameter>): Parameter =
        OperationParameter(type, parameters)

}
