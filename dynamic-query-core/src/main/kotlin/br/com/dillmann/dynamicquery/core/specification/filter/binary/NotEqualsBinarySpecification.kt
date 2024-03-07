package br.com.dillmann.dynamicquery.core.specification.filter.binary

import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for not equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotEqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::notEqual)
