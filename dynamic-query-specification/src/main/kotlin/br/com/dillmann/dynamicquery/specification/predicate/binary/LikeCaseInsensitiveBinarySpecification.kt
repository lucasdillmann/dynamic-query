package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.decorators.caseInsensitive
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for case-insensitive like expressions
 *
 * @param target Target of the operation (such as the attribute name)
 * @param value Value to be compared to
 */
internal class LikeCaseInsensitiveBinarySpecification(target: Parameter, value: Parameter):
    BinarySpecification(target, value, caseInsensitive("like", CriteriaBuilder::like))
