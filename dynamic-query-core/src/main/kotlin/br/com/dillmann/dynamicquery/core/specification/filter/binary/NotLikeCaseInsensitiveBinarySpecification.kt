package br.com.dillmann.dynamicquery.core.specification.filter.binary

import br.com.dillmann.dynamicquery.core.specification.decorators.caseInsensitive
import javax.persistence.criteria.*

/**
 * [BinarySpecification] implementation for case-insensitive not like expressions
 *
 * @param attributeName Full path of the attribute
 * @param value Value to be compared to
 */
class NotLikeCaseInsensitiveBinarySpecification(attributeName: String, value: String):
    BinarySpecification(attributeName, value, caseInsensitive("notLike", CriteriaBuilder::notLike))
