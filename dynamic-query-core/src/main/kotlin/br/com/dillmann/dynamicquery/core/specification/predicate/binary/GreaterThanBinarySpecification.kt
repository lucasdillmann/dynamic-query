package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for greater than expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class GreaterThanBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::greaterThan)
