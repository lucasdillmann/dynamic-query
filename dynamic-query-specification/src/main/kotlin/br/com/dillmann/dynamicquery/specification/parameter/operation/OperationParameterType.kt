package br.com.dillmann.dynamicquery.specification.parameter.operation

import br.com.dillmann.dynamicquery.specification.NO_ELEMENT_RANGE
import br.com.dillmann.dynamicquery.specification.ONE_ELEMENT_RANGE
import br.com.dillmann.dynamicquery.specification.ONE_OR_TWO_ELEMENTS
import br.com.dillmann.dynamicquery.specification.TWO_ELEMENTS_RANGE
import br.com.dillmann.dynamicquery.specification.TWO_OR_THREE_ELEMENTS_RANGE

/**
 * Relation of supported operation types
 *
 * @property identifier Unique identifier of the type
 * @property argumentCountRange Range of accepted argument count
 */
enum class OperationParameterType(val identifier: String, val argumentCountRange: IntRange) {

    ABS("abs", ONE_ELEMENT_RANGE),
    AVG("avg", ONE_ELEMENT_RANGE),
    CEILING("ceiling", ONE_ELEMENT_RANGE),
    COALESCE("coalesce", TWO_ELEMENTS_RANGE),
    CONCAT("concat", TWO_ELEMENTS_RANGE),
    COUNT("count", ONE_ELEMENT_RANGE),
    COUNT_DISTINCT("countDistinct", ONE_ELEMENT_RANGE),
    CURRENT_DATE("currentDate", NO_ELEMENT_RANGE),
    CURRENT_TIME("currentTime", NO_ELEMENT_RANGE),
    CURRENT_TIMESTAMP("currentTimestamp", NO_ELEMENT_RANGE),
    DIFF("diff", TWO_ELEMENTS_RANGE),
    EXP("exp", ONE_ELEMENT_RANGE),
    FLOOR("floor", ONE_ELEMENT_RANGE),
    LENGTH("length", ONE_ELEMENT_RANGE),
    LOCAL_DATE("localDate", NO_ELEMENT_RANGE),
    LOCAL_DATE_TIME("localDateTime", NO_ELEMENT_RANGE),
    LOCAL_TIME("localTime", NO_ELEMENT_RANGE),
    LOCATE("locate", TWO_ELEMENTS_RANGE),
    LOWER("lower", ONE_ELEMENT_RANGE),
    MAX("max", ONE_ELEMENT_RANGE),
    MIN("min", ONE_ELEMENT_RANGE),
    MOD("mod", TWO_ELEMENTS_RANGE),
    NEG("neg", ONE_ELEMENT_RANGE),
    POWER("power", TWO_ELEMENTS_RANGE),
    PROD("prod", TWO_ELEMENTS_RANGE),
    QUOT("quot", TWO_ELEMENTS_RANGE),
    SIZE("size", ONE_ELEMENT_RANGE),
    SQRT("sqrt", ONE_ELEMENT_RANGE),
    SUBSTRING("substring", TWO_OR_THREE_ELEMENTS_RANGE),
    SUM("sum", ONE_OR_TWO_ELEMENTS),
    TO_BIG_DECIMAL("toBigDecimal", ONE_ELEMENT_RANGE),
    TO_BIG_INTEGER("toBigInteger", ONE_ELEMENT_RANGE),
    TO_DOUBLE("toDouble", ONE_ELEMENT_RANGE),
    TO_FLOAT("toFloat", ONE_ELEMENT_RANGE),
    TO_INTEGER("toInteger", ONE_ELEMENT_RANGE),
    TO_LONG("toLong", ONE_ELEMENT_RANGE),
    TRIM("trim", ONE_ELEMENT_RANGE),
    UPPER("upper", ONE_ELEMENT_RANGE);

    companion object {

        /**
         * Resolves and returns a [OperationParameterType] by its identifier
         *
         * @param identifier Identifier of the wanted option
         */
        @JvmStatic
        fun forIdentifier(identifier: String) =
            entries.first { it.identifier == identifier }
    }
}
