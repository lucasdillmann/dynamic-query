package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.requireString
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for like expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class LikeBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, requireString("like", CriteriaBuilder::like))
