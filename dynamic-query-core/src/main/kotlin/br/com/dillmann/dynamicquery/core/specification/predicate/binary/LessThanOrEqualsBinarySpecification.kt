package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for less than or equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class LessThanOrEqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::lessThanOrEqualTo)
