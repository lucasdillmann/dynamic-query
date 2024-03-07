package br.com.dillmann.dynamicquery.core.specification.filter.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.requireString
import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for not like expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotLikeBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, requireString("notLike", CriteriaBuilder::notLike))
