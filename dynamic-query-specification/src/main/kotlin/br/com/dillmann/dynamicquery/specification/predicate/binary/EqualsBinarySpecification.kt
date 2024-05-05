package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for equals expressions
 *
 * @param target Target of the operation (such as the attribute name)
 * @param value Value to be compared to
 */
internal class EqualsBinarySpecification(target: Parameter, value: Parameter):
    BinarySpecification(target, value, CriteriaBuilder::equal)
