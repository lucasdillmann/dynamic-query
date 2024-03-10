package br.com.dillmann.dynamicquery.core.specification.predicate.collection

/**
 * [CollectionSpecification] implementation for not in expressions
 *
 * @param attributeName Full path of the attribute
 * @param values Values to be compared to
 */
class NotInCollectionSpecification(attributeName: String, values: List<String>):
    CollectionSpecification(attributeName, values, true)
