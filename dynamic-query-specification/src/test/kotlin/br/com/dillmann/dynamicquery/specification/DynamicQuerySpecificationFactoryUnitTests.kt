package br.com.dillmann.dynamicquery.specification

import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.specification.group.AndGroupSpecification
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.group.OrGroupSpecification
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType
import br.com.dillmann.dynamicquery.specification.predicate.binary.*
import br.com.dillmann.dynamicquery.specification.predicate.collection.CollectionSpecification
import br.com.dillmann.dynamicquery.specification.predicate.collection.InCollectionSpecification
import br.com.dillmann.dynamicquery.specification.predicate.collection.NotInCollectionSpecification
import br.com.dillmann.dynamicquery.specification.predicate.negation.NegationSpecification
import br.com.dillmann.dynamicquery.specification.predicate.range.BetweenRangeSpecification
import br.com.dillmann.dynamicquery.specification.predicate.range.NotBetweenRangeSpecification
import br.com.dillmann.dynamicquery.specification.predicate.range.RangeSpecification
import br.com.dillmann.dynamicquery.specification.predicate.unary.*
import io.mockk.mockk
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

/**
 * [DynamicQuerySpecificationFactory] unit tests
 */
class DynamicQuerySpecificationFactoryUnitTests {

    @Test
    fun `predicate should return the expected specification when asked for an IS_NULL`() =
        testUnaryPredicate<IsNullUnarySpecification>(PredicateType.IS_NULL)

    @Test
    fun `predicate should return the expected specification when asked for an IS_NOT_NULL`() =
        testUnaryPredicate<IsNotNullUnarySpecification>(PredicateType.IS_NOT_NULL)

    @Test
    fun `predicate should return the expected specification when asked for an IS_EMPTY`()  =
        testUnaryPredicate<IsEmptyUnarySpecification>(PredicateType.IS_EMPTY)

    @Test
    fun `predicate should return the expected specification when asked for an IS_NOT_EMPTY`()  =
        testUnaryPredicate<IsNotEmptyUnarySpecification>(PredicateType.IS_NOT_EMPTY)

    @Test
    fun `predicate should return the expected specification when asked for a BETWEEN`() =
        testRangePredicate<BetweenRangeSpecification>(PredicateType.BETWEEN)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_BETWEEN`() =
        testRangePredicate<NotBetweenRangeSpecification>(PredicateType.NOT_BETWEEN)

    @Test
    fun `predicate should return the expected specification when asked for an IN`() =
        testCollectionPredicate<InCollectionSpecification>(PredicateType.IN)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_IN`() =
        testCollectionPredicate<NotInCollectionSpecification>(PredicateType.NOT_IN)

    @Test
    fun `predicate should return the expected specification when asked for an EQUALS`() =
        testBinaryPredicate<EqualsBinarySpecification>(PredicateType.EQUALS)

    @Test
    fun `predicate should return the expected specification when asked for an EQUALS_IGNORE_CASE`() =
        testBinaryPredicate<EqualsCaseInsensitiveBinarySpecification>(PredicateType.EQUALS_IGNORE_CASE)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_EQUALS`() =
        testBinaryPredicate<NotEqualsBinarySpecification>(PredicateType.NOT_EQUALS)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_EQUALS_IGNORE_CASE`() =
        testBinaryPredicate<NotEqualsCaseInsensitiveBinarySpecification>(PredicateType.NOT_EQUALS_IGNORE_CASE)

    @Test
    fun `predicate should return the expected specification when asked for a GREATER_THAN`() =
        testBinaryPredicate<GreaterThanBinarySpecification>(PredicateType.GREATER_THAN)

    @Test
    fun `predicate should return the expected specification when asked for a GREATER_THAN_OR_EQUALS`() =
        testBinaryPredicate<GreaterThanOrEqualsBinarySpecification>(PredicateType.GREATER_THAN_OR_EQUALS)

    @Test
    fun `predicate should return the expected specification when asked for a LESS_THAN`() =
        testBinaryPredicate<LessThanBinarySpecification>(PredicateType.LESS_THAN)

    @Test
    fun `predicate should return the expected specification when asked for a LESS_THAN_OR_EQUALS`() =
        testBinaryPredicate<LessThanOrEqualsBinarySpecification>(PredicateType.LESS_THAN_OR_EQUALS)

    @Test
    fun `predicate should return the expected specification when asked for a LIKE`() =
        testBinaryPredicate<LikeBinarySpecification>(PredicateType.LIKE)

    @Test
    fun `predicate should return the expected specification when asked for a LIKE_IGNORE_CASE`() =
        testBinaryPredicate<LikeCaseInsensitiveBinarySpecification>(PredicateType.LIKE_IGNORE_CASE)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_LIKE`() =
        testBinaryPredicate<NotLikeBinarySpecification>(PredicateType.NOT_LIKE)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_LIKE_IGNORE_CASE`() =
        testBinaryPredicate<NotLikeCaseInsensitiveBinarySpecification>(PredicateType.NOT_LIKE_IGNORE_CASE)

    @Test
    fun `negate should return the expected specification`() {
        // scenario
        val specification = mockk<DynamicQuerySpecification>()

        // execution
        val result = DynamicQuerySpecificationFactory.negate(specification)

        // validation
        check(result is NegationSpecification)
        assertEquals(specification, result.negatedExpression)
    }

    @Test
    fun `group should return the expected specification when called with the AND logical operator`() {
        // scenario
        val left = mockk<DynamicQuerySpecification>()
        val right = mockk<DynamicQuerySpecification>()

        // execution
        val result = DynamicQuerySpecificationFactory.group(LogicalOperatorType.AND, left, right)

        // validation
        check(result is AndGroupSpecification)
        assertEquals(left, result.leftExpression)
        assertEquals(right, result.rightExpression)
    }

    @Test
    fun `group should return the expected specification when called with the OR logical operator`() {
        // scenario
        val left = mockk<DynamicQuerySpecification>()
        val right = mockk<DynamicQuerySpecification>()

        // execution
        val result = DynamicQuerySpecificationFactory.group(LogicalOperatorType.OR, left, right)

        // validation
        check(result is OrGroupSpecification)
        assertEquals(left, result.leftExpression)
        assertEquals(right, result.rightExpression)
    }

    @ParameterizedTest
    @EnumSource(value = PredicateType::class)
    fun `predicate should throw InvalidArgumentCountException when called with less arguments than expected`(
        predicateType: PredicateType,
    ) {
        // condition
        val range = predicateType.argumentCountRange
        Assumptions.assumeFalse(range.last == 0)

        // scenario
        val arguments = randomListOf(minimumSize = 0, maximumSize = range.first - 1) { mockk<Parameter>() }

        // execution
        val result = runCatching { DynamicQuerySpecificationFactory.predicate(predicateType, arguments) }

        // validation
        assertInvalidArgumentCountException(result, predicateType, arguments.size)
    }

    @ParameterizedTest
    @EnumSource(value = PredicateType::class)
    fun `predicate should throw InvalidArgumentCountException when called with more arguments than expected`(
        predicateType: PredicateType,
    ) {
        // scenario
        val range = predicateType.argumentCountRange
        val arguments = randomListOf(minimumSize = range.last + 1, maximumSize = range.last + 10) { mockk<Parameter>() }

        // execution
        val result = runCatching { DynamicQuerySpecificationFactory.predicate(predicateType, arguments) }

        // validation
        assertInvalidArgumentCountException(result, predicateType, arguments.size)
    }

    private inline fun <reified T: UnarySpecification> testUnaryPredicate(type: PredicateType) {
        // scenario
        val attributeName = mockk<Parameter>()

        // execution
        val result = DynamicQuerySpecificationFactory.predicate(type, listOf(attributeName))

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(attributeName, specification.target)
    }

    private inline fun <reified T: RangeSpecification> testRangePredicate(type: PredicateType) {
        // scenario
        val arguments = (0..2).map { mockk<Parameter>() }

        // execution
        val result = DynamicQuerySpecificationFactory.predicate(type, arguments)

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(arguments[0], specification.target)
        assertEquals(arguments[1], specification.rangeStartValue)
        assertEquals(arguments[2], specification.rangeEndValue)
    }

    private inline fun <reified T: CollectionSpecification> testCollectionPredicate(type: PredicateType) {
        // scenario
        val arguments = listOf<Parameter>(mockk(), mockk())

        // execution
        val result = DynamicQuerySpecificationFactory.predicate(type, arguments)

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(arguments.first(), specification.target)
        assertEquals(listOf(arguments.last()), specification.values)
    }

    private inline fun <reified T: BinarySpecification> testBinaryPredicate(type: PredicateType) {
        // scenario
        val attributeName = mockk<Parameter>()
        val argument = mockk<Parameter>()

        // execution
        val result = DynamicQuerySpecificationFactory.predicate(type, listOf(attributeName, argument))

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertSame(attributeName, specification.target)
        assertSame(argument, specification.value)
    }

    private fun assertInvalidArgumentCountException(
        result: Result<*>,
        predicateType: PredicateType,
        argumentCount: Int,
    ) {
        val exception = result.exceptionOrNull() as? InvalidArgumentCountException
        assertNotNull(exception)

        val argumentCountRange = predicateType.argumentCountRange
        assertEquals(predicateType.identifier, exception.operation)
        assertEquals(argumentCount, exception.currentArgumentCount)
        assertEquals(argumentCountRange.first, exception.minimumArgumentCount)
        assertEquals(argumentCountRange.last, exception.maximumArgumentCount)
    }

}
