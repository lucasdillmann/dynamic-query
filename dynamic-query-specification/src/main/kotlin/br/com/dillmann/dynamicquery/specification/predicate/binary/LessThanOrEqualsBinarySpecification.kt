package br.com.dillmann.dynamicquery.specification.predicate.binary

import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for less than or equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class LessThanOrEqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::lessThanOrEqualTo)
