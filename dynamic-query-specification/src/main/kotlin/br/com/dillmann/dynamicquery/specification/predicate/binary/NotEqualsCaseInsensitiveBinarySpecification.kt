package br.com.dillmann.dynamicquery.specification.predicate.binary

import br.com.dillmann.dynamicquery.specification.decorators.caseInsensitive
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for not equals expressions that are case-insensitive
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotEqualsCaseInsensitiveBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, caseInsensitive("notEqual", CriteriaBuilder::notEqual))
