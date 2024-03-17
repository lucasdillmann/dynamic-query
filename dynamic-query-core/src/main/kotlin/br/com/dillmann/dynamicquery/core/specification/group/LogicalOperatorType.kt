package br.com.dillmann.dynamicquery.core.specification.group

/**
 * Relation of logical operators
 *
 * @property identifier Unique identifier of the operator
 */
enum class LogicalOperatorType(val identifier: String) {

    /**
     * Logical operator for the OR condition, where the output will be true if either or both of the inputs are true
     */
    OR("||"),

    /**
     * Logical operator for the AND condition, where the output will be true only when both of the inputs are true
     */
    AND("&&");

    companion object {

        /**
         * Resolves and returns a [LogicalOperatorType] by its identifier
         *
         * @param identifier Identifier of the wanted option
         */
        @JvmStatic
        fun forIdentifier(identifier: String) =
            entries.first { it.identifier == identifier }
    }

}
