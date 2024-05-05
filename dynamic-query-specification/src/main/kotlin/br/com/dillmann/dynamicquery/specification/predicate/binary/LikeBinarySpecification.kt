package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.decorators.requireString
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for like expressions
 *
 * @param target Target of the operation (such as the attribute name)
 * @param value Value to be compared to
 */
internal class LikeBinarySpecification(target: Parameter, value: Parameter):
    BinarySpecification(target, value, requireString("like", CriteriaBuilder::like))
