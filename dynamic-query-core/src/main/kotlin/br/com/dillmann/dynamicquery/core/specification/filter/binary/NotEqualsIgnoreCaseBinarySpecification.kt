package br.com.dillmann.dynamicquery.core.specification.filter.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.caseInsensitive
import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for not equals expressions that are case-insensitive
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotEqualsIgnoreCaseBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, caseInsensitive("notEqual", CriteriaBuilder::notEqual))
