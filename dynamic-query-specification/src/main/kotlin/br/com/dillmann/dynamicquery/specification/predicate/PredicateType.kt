package br.com.dillmann.dynamicquery.specification.predicate

import br.com.dillmann.dynamicquery.specification.AT_LEAST_TWO_ELEMENTS_RANGE
import br.com.dillmann.dynamicquery.specification.ONE_ELEMENT_RANGE
import br.com.dillmann.dynamicquery.specification.THREE_ELEMENTS_RANGE
import br.com.dillmann.dynamicquery.specification.TWO_ELEMENTS_RANGE

/**
 * Relation of supported predicate types
 *
 * @property identifier Unique identifier of the type
 * @property argumentCountRange Range of accepted argument count
 */
enum class PredicateType(val identifier: String, val argumentCountRange: IntRange) {

    // Unary
    IS_NULL("isNull", ONE_ELEMENT_RANGE),
    IS_NOT_NULL("isNotNull", ONE_ELEMENT_RANGE),
    IS_EMPTY("isEmpty", ONE_ELEMENT_RANGE),
    IS_NOT_EMPTY("isNotEmpty", ONE_ELEMENT_RANGE),

    // Range
    BETWEEN("between", THREE_ELEMENTS_RANGE),
    NOT_BETWEEN("notBetween", THREE_ELEMENTS_RANGE),

    // Collection
    IN("in", AT_LEAST_TWO_ELEMENTS_RANGE),
    NOT_IN("notIn", AT_LEAST_TWO_ELEMENTS_RANGE),

    // Binary
    EQUALS("equals", TWO_ELEMENTS_RANGE),
    EQUALS_IGNORE_CASE("equalsIgnoreCase", TWO_ELEMENTS_RANGE),
    NOT_EQUALS("notEquals", TWO_ELEMENTS_RANGE),
    NOT_EQUALS_IGNORE_CASE("notEqualsIgnoreCase", TWO_ELEMENTS_RANGE),
    GREATER_THAN("greaterThan", TWO_ELEMENTS_RANGE),
    GREATER_THAN_OR_EQUALS("greaterOrEquals", TWO_ELEMENTS_RANGE),
    LESS_THAN("lessThan", TWO_ELEMENTS_RANGE),
    LESS_THAN_OR_EQUALS("lessOrEquals", TWO_ELEMENTS_RANGE),
    LIKE("like", TWO_ELEMENTS_RANGE),
    LIKE_IGNORE_CASE("likeIgnoreCase", TWO_ELEMENTS_RANGE),
    NOT_LIKE("notLike", TWO_ELEMENTS_RANGE),
    NOT_LIKE_IGNORE_CASE("notLikeIgnoreCase", TWO_ELEMENTS_RANGE);

    companion object {

        /**
         * Resolves and returns a [PredicateType] by its identifier
         *
         * @param identifier Identifier of the wanted option
         */
        @JvmStatic
        fun forIdentifier(identifier: String) =
            entries.first { it.identifier == identifier }
    }
}
