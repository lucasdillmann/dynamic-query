package br.com.dillmann.dynamicquery.core.specification.predicate.unary

import br.com.dillmann.dynamicquery.core.specification.decorators.requireCollection
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is not empty operator
 *
 * @param attributeName Name of the attribute
 */
class IsNotEmptyUnarySpecification(attributeName: String):
    UnarySpecification(attributeName, requireCollection(CriteriaBuilder::isNotEmpty))
