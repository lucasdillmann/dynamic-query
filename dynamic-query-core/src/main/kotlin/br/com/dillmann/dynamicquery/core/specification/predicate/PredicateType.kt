package br.com.dillmann.dynamicquery.core.specification.predicate

private val EMPTY_RANGE = 0..0

/**
 * Relation of supported predicate types
 *
 * @property identifier Unique identifier of the type
 * @property argumentCountRange Range of accepted argument count
 */
enum class PredicateType(val identifier: String, val argumentCountRange: IntRange) {

    // Unary
    IS_NULL("isNull", EMPTY_RANGE),
    IS_NOT_NULL("isNotNull", EMPTY_RANGE),
    IS_EMPTY("isEmpty", EMPTY_RANGE),
    IS_NOT_EMPTY("isNotEmpty", EMPTY_RANGE),

    // Range
    BETWEEN("between", 2..2),
    NOT_BETWEEN("notBetween", 2..2),

    // Collection
    IN("in", 1..Int.MAX_VALUE),
    NOT_IN("notIn", 1..Int.MAX_VALUE),

    // Binary
    EQUALS("equals", 1..1),
    EQUALS_IGNORE_CASE("equalsIgnoreCase", 1..1),
    NOT_EQUALS("notEquals", 1..1),
    NOT_EQUALS_IGNORE_CASE("notEqualsIgnoreCase", 1..1),
    GREATER_THAN("greaterThan", 1..1),
    GREATER_THAN_OR_EQUALS("greaterOrEquals", 1..1),
    LESS_THAN("lessThan", 1..1),
    LESS_THAN_OR_EQUALS("lessOrEquals", 1..1),
    LIKE("like", 1..1),
    LIKE_IGNORE_CASE("likeIgnoreCase", 1..1),
    NOT_LIKE("notLike", 1..1),
    NOT_LIKE_IGNORE_CASE("notLikeIgnoreCase", 1..1);

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
