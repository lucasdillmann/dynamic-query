package br.com.dillmann.dynamicquery.core.specification.predicate.collection

import br.com.dillmann.dynamicquery.core.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.core.specification.path.PathResolver
import br.com.dillmann.dynamicquery.core.valueparser.ValueParsers
import javax.persistence.criteria.*

/**
 * [PredicateSpecification] specialization for collection filter expressions
 *
 * @param attributeName Full path of the attribute
 * @param values Values to be compared to
 * @param negateResults Defines if the predicate should be negated or not
 */
abstract class CollectionSpecification(
    val attributeName: String,
    val values: List<String>,
    private val negateResults: Boolean,
): PredicateSpecification {

    /**
     * Produces a JPA-compliant predicate using the given criteria
     *
     * @param root Root of the query
     * @param query Query being built
     * @param builder Criteria expression builder
     */
    override fun toPredicate(root: Root<*>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate {
        val path = PathResolver.resolve(attributeName, root)
        val predicate = builder.`in`(path)
        values
            .map { ValueParsers.parse(it, path.javaType) }
            .forEach { predicate.value(it) }

        return if (negateResults) predicate.not() else predicate
    }
}
