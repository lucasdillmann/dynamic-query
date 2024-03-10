package br.com.dillmann.dynamicquery.core.specification.predicate.collection

/**
 * [CollectionSpecification] implementation for in expressions
 *
 * @param attributeName Full path of the attribute
 * @param values Values to be compared to
 */
class InCollectionSpecification(attributeName: String, values: List<String>):
    CollectionSpecification(attributeName, values, false)
