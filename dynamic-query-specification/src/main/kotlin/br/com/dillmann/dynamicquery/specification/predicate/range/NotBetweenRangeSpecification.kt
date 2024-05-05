package br.com.dillmann.dynamicquery.specification.predicate.range

import br.com.dillmann.dynamicquery.specification.parameter.Parameter

/**
 * [RangeSpecification] implementation for the "not between" operator
 *
 * @param target Target of the operation (such as the attribute name)
 * @param rangeStartValue Left (start) value of the range
 * @param rangeEndValue Left (end) value of the range
 */
internal class NotBetweenRangeSpecification(target: Parameter, rangeStartValue: Parameter, rangeEndValue: Parameter):
    RangeSpecification(target, rangeStartValue, rangeEndValue, true)
