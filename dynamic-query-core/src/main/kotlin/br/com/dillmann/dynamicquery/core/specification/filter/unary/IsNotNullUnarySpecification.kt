package br.com.dillmann.dynamicquery.core.specification.filter.unary

import javax.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is null operator
 *
 * @param attributeName Name of the attribute
 */
class IsNotNullUnarySpecification(attributeName: String):
    UnarySpecification(attributeName, CriteriaBuilder::isNotNull)
