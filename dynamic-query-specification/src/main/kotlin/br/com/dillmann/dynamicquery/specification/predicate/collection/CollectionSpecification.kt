package br.com.dillmann.dynamicquery.specification.predicate.collection

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

/**
 * [PredicateSpecification] specialization for collection filter expressions
 *
 * @property target Target of the operation (such as the attribute name)
 * @property values Values to be compared to
 * @param negateResults Defines if the predicate should be negated or not
 */
internal abstract class CollectionSpecification(
    val target: Parameter,
    val values: List<Parameter>,
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
        val path = target.asExpression(root, query, builder)
        val predicate = builder.`in`(path)
        values
            .map { it.asExpression(root, query, builder, path.javaType) }
            .forEach { predicate.value(it) }

        return if (negateResults) predicate.not() else predicate
    }
}
