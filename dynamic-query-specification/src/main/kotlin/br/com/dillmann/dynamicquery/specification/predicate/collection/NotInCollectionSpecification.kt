package br.com.dillmann.dynamicquery.specification.predicate.collection

import br.com.dillmann.dynamicquery.specification.parameter.Parameter

/**
 * [CollectionSpecification] implementation for not in expressions
 *
 * @param target Target of the operation (such as the attribute name)
 * @param values Values to be compared to
 */
internal class NotInCollectionSpecification(target: Parameter, values: List<Parameter>):
    CollectionSpecification(target, values, true)
