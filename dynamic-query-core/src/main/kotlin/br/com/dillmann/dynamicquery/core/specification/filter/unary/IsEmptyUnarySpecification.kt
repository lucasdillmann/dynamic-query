package br.com.dillmann.dynamicquery.core.specification.filter.unary

import br.com.dillmann.dynamicquery.core.specification.decorators.requireCollection
import javax.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is empty operator
 *
 * @param attributeName Name of the attribute
 */
class IsEmptyUnarySpecification(attributeName: String):
    UnarySpecification(attributeName, requireCollection(CriteriaBuilder::isEmpty))
