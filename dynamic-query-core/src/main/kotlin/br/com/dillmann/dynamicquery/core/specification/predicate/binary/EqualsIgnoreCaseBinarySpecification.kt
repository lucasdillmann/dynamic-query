package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.caseInsensitive
import javax.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for not equals expressions that are case-insensitive
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class EqualsIgnoreCaseBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, caseInsensitive("equal", CriteriaBuilder::equal))
