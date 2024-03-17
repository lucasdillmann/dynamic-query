package br.com.dillmann.dynamicquery.core.valueparser

/**
 * [Comparator] implementation able to sort two [ValueParser] implementation using its priority values in a
 * ascending way
 */
internal object ValueParserComparator: Comparator<ValueParser<*>> {

    /**
     * Compares two [ValueParser] using its priorities
     *
     * @param left Left value to be compared
     * @param right Right value to be compared
     */
    override fun compare(left: ValueParser<*>, right: ValueParser<*>): Int =
        left.priority.compareTo(right.priority)
}
