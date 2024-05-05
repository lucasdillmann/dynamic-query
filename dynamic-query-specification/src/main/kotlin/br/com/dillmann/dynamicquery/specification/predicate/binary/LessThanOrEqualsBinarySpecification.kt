package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for less than or equals expressions
 *
 * @param target Target of the operation (such as the attribute name)
 * @param value Value to be compared to
 */
internal class LessThanOrEqualsBinarySpecification(target: Parameter, value: Parameter):
    BinarySpecification(target, value, CriteriaBuilder::lessThanOrEqualTo)
