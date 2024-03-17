package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.requireString
import javax.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for not like expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotLikeBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, requireString("notLike", CriteriaBuilder::notLike))
