package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.caseInsensitive
import javax.persistence.criteria.CriteriaBuilder

/**
 * [BinarySpecification] implementation for case-insensitive like expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class LikeCaseInsensitiveBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, caseInsensitive("like", CriteriaBuilder::like))
