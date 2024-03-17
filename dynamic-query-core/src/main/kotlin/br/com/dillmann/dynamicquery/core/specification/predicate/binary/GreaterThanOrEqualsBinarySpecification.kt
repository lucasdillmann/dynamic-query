package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for bigger than or equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class GreaterThanOrEqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::greaterThanOrEqualTo)
