package br.com.dillmann.dynamicquery.specification.predicate.unary

import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is null operator
 *
 * @param attributeName Name of the attribute
 */
class IsNullUnarySpecification(attributeName: String):
    UnarySpecification(attributeName, CriteriaBuilder::isNull)
