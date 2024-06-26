package br.com.dillmann.dynamicquery.grammar

private val ASCII_CHARS = (32..126).map(Int::toChar)

/**
 * Produces and returns a [String] with random size and contents
 */
val randomString: String
    get() = randomListOf(minimumSize = 1, maximumSize = 50) { ASCII_CHARS.random() }.joinToString(separator = "")

/**
 * Produces and returns a random [Long] between the maximum and minimum values that the type can hold
 */
val randomLong: Long
    get() = (Long.MIN_VALUE..Long.MAX_VALUE).random()

/**
 * Produces and returns a random [Int] between the maximum and minimum values that the type can hold
 */
val randomInt: Int
    get() = (Int.MIN_VALUE..Int.MAX_VALUE).random()

/**
 * Produces and returns a random [Boolean]
 */
val randomBoolean: Boolean
    get() = listOf(true, false).random()

/**
 * Produces and returns a [List] with random size (withing the provided bounds) where each element is produced
 * by the given provider
 *
 * @param T Generic type of the values
 * @param minimumSize Minimum size of the list. Defaults to 1.
 * @param maximumSize Maximum size of the list. Defaults to 10.
 * @param valueProvider Function to be called to produce each position of the list
 */
fun <T: Any?> randomListOf(minimumSize: Int = 1, maximumSize: Int = 10, valueProvider: (Int) -> T): List<T> =
    if (maximumSize == 0) emptyList()
    else (minimumSize..maximumSize).random().downTo(1).map(valueProvider)
