package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [UnarySpecification] implementation for the is null operator
 *
 * @param target Target of the operation (such as the attribute name)
 */
internal class IsNullUnarySpecification(target: Parameter):
    UnarySpecification(target, CriteriaBuilder::isNull)
