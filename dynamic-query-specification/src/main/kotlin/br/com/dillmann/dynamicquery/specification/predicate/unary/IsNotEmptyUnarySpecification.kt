package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.decorators.requireCollection
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is not empty operator
 *
 * @param target Target of the operation (such as the attribute name)
 */
internal class IsNotEmptyUnarySpecification(target: Parameter):
    UnarySpecification(target, requireCollection(CriteriaBuilder::isNotEmpty))
