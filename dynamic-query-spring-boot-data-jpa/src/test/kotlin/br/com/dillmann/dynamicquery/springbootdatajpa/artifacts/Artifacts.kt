package br.com.dillmann.dynamicquery.springbootdatajpa.artifacts

/**
 * Produces and returns a random [Long] between the maximum and minimum values that the type can hold
 */
val randomLong: Long
    get() = (Long.MIN_VALUE..Long.MAX_VALUE).random()

/**
 * Produces and returns a random [Boolean]
 */
val randomBoolean: Boolean
    get() = listOf(true, false).random()
