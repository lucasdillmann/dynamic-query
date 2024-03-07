package br.com.dillmann.dynamicquery.core.specification.filter.binary

import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for greater than expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class GreaterThanBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::greaterThan)
