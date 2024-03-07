package br.com.dillmann.dynamicquery.core.specification.filter.range

/**
 * [RangeSpecification] implementation for the not between operator
 *
 * @param attributeName Name of the attribute
 * @param rangeStartValue Left (start) value of the range
 * @param rangeEndValue Left (end) value of the range
 */
class NotBetweenRangeSpecification(attributeName: String, rangeStartValue: String, rangeEndValue: String):
    RangeSpecification(attributeName, rangeStartValue, rangeEndValue, true)
