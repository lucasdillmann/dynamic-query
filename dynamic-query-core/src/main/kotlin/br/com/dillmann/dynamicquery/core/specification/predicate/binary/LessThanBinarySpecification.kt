package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for less than expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class LessThanBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::lessThan)
