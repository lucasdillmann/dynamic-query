package br.com.dillmann.dynamicquery.core.specification.filter.binary

import javax.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for equals expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class EqualsBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, CriteriaBuilder::equal)
