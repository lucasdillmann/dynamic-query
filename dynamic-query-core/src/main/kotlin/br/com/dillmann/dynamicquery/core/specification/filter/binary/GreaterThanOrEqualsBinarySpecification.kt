package br.com.dillmann.dynamicquery.core.specification.filter.binary

import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for bigger than or equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class GreaterThanOrEqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::greaterThanOrEqualTo)
