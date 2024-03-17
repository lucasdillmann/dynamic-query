package br.com.dillmann.dynamicquery.core.specification.predicate

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * [PredicateType] unit tests
 */
class PredicateTypeUnitTests {

    @Test
    fun `IS_NULL option should have the expected identifier`() =
        assertEquals("isNull", PredicateType.IS_NULL.identifier)

    @Test
    fun `IS_NOT_NULL option should have the expected identifier`() =
        assertEquals("isNotNull", PredicateType.IS_NOT_NULL.identifier)

    @Test
    fun `IS_EMPTY option should have the expected identifier`() =
        assertEquals("isEmpty", PredicateType.IS_EMPTY.identifier)

    @Test
    fun `IS_NOT_EMPTY option should have the expected identifier`() =
        assertEquals("isNotEmpty", PredicateType.IS_NOT_EMPTY.identifier)

    @Test
    fun `BETWEEN option should have the expected identifier`() =
        assertEquals("between", PredicateType.BETWEEN.identifier)

    @Test
    fun `NOT_BETWEEN option should have the expected identifier`() =
        assertEquals("notBetween", PredicateType.NOT_BETWEEN.identifier)

    @Test
    fun `IN option should have the expected identifier`() =
        assertEquals("in", PredicateType.IN.identifier)

    @Test
    fun `NOT_IN option should have the expected identifier`() =
        assertEquals("notIn", PredicateType.NOT_IN.identifier)

    @Test
    fun `EQUALS option should have the expected identifier`() =
        assertEquals("equals", PredicateType.EQUALS.identifier)

    @Test
    fun `EQUALS_IGNORE_CASE option should have the expected identifier`() =
        assertEquals("equalsIgnoreCase", PredicateType.EQUALS_IGNORE_CASE.identifier)

    @Test
    fun `NOT_EQUALS option should have the expected identifier`() =
        assertEquals("notEquals", PredicateType.NOT_EQUALS.identifier)

    @Test
    fun `NOT_EQUALS_IGNORE_CASE option should have the expected identifier`() =
        assertEquals("notEqualsIgnoreCase", PredicateType.NOT_EQUALS_IGNORE_CASE.identifier)

    @Test
    fun `GREATER_THAN option should have the expected identifier`() =
        assertEquals("greaterThan", PredicateType.GREATER_THAN.identifier)

    @Test
    fun `GREATER_THAN_OR_EQUALS option should have the expected identifier`() =
        assertEquals("greaterOrEquals", PredicateType.GREATER_THAN_OR_EQUALS.identifier)

    @Test
    fun `LESS_THAN should have the expected identifier`() =
        assertEquals("lessThan", PredicateType.LESS_THAN.identifier)

    @Test
    fun `LESS_THAN_OR_EQUALS option should have the expected identifier`() =
        assertEquals("lessOrEquals", PredicateType.LESS_THAN_OR_EQUALS.identifier)

    @Test
    fun `LIKE option should have the expected identifier`() =
        assertEquals("like", PredicateType.LIKE.identifier)

    @Test
    fun `LIKE_IGNORE_CASE option should have the expected identifier`() =
        assertEquals("likeIgnoreCase", PredicateType.LIKE_IGNORE_CASE.identifier)

    @Test
    fun `NOT_LIKE option should have the expected identifier`() =
        assertEquals("notLike", PredicateType.NOT_LIKE.identifier)

    @Test
    fun `NOT_LIKE_IGNORE_CASE option should have the expected identifier`() =
        assertEquals("notLikeIgnoreCase", PredicateType.NOT_LIKE_IGNORE_CASE.identifier)

    @Test
    fun `IS_NULL option should have the expected argument count range`() =
        assertEquals(0..0, PredicateType.IS_NULL.argumentCountRange)

    @Test
    fun `IS_NOT_NULL option should have the expected argument count range`() =
        assertEquals(0..0, PredicateType.IS_NOT_NULL.argumentCountRange)

    @Test
    fun `IS_EMPTY option should have the expected argument count range`() =
        assertEquals(0..0, PredicateType.IS_EMPTY.argumentCountRange)

    @Test
    fun `IS_NOT_EMPTY option should have the expected argument count range`() =
        assertEquals(0..0, PredicateType.IS_NOT_EMPTY.argumentCountRange)

    @Test
    fun `BETWEEN option should have the expected argument count range`() =
        assertEquals(2..2, PredicateType.BETWEEN.argumentCountRange)

    @Test
    fun `NOT_BETWEEN option should have the expected argument count range`() =
        assertEquals(2..2, PredicateType.NOT_BETWEEN.argumentCountRange)

    @Test
    fun `IN option should have the expected argument count range`() =
        assertEquals(1..Int.MAX_VALUE, PredicateType.IN.argumentCountRange)

    @Test
    fun `NOT_IN option should have the expected argument count range`() =
        assertEquals(1..Int.MAX_VALUE, PredicateType.NOT_IN.argumentCountRange)

    @Test
    fun `EQUALS option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.EQUALS.argumentCountRange)

    @Test
    fun `EQUALS_IGNORE_CASE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.EQUALS_IGNORE_CASE.argumentCountRange)

    @Test
    fun `NOT_EQUALS option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.NOT_EQUALS.argumentCountRange)

    @Test
    fun `NOT_EQUALS_IGNORE_CASE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.NOT_EQUALS_IGNORE_CASE.argumentCountRange)

    @Test
    fun `GREATER_THAN option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.GREATER_THAN.argumentCountRange)

    @Test
    fun `GREATER_THAN_OR_EQUALS option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.GREATER_THAN_OR_EQUALS.argumentCountRange)

    @Test
    fun `LESS_THAN option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.LESS_THAN.argumentCountRange)

    @Test
    fun `LESS_THAN_OR_EQUALS option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.LESS_THAN_OR_EQUALS.argumentCountRange)

    @Test
    fun `LIKE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.LIKE.argumentCountRange)

    @Test
    fun `LIKE_IGNORE_CASE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.LIKE_IGNORE_CASE.argumentCountRange)

    @Test
    fun `NOT_LIKE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.NOT_LIKE.argumentCountRange)

    @Test
    fun `NOT_LIKE_IGNORE_CASE option should have the expected argument count range`() =
        assertEquals(1..1, PredicateType.NOT_LIKE_IGNORE_CASE.argumentCountRange)

}
