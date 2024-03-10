package br.com.dillmann.dynamicquery.core.specification.predicate.range

/**
 * [RangeSpecification] implementation for the between operator
 *
 * @param attributeName Name of the attribute
 * @param rangeStartValue Left (start) value of the range
 * @param rangeEndValue Left (end) value of the range
 */
class BetweenRangeSpecification(attributeName: String, rangeStartValue: String, rangeEndValue: String):
    RangeSpecification(attributeName, rangeStartValue, rangeEndValue, false)
