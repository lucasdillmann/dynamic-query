package br.com.dillmann.dynamicquery.core.specification

import br.com.dillmann.dynamicquery.core.randomListOf
import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.core.specification.filter.PredicateType
import br.com.dillmann.dynamicquery.core.specification.filter.binary.*
import br.com.dillmann.dynamicquery.core.specification.filter.collection.CollectionSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.collection.InCollectionSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.collection.NotInCollectionSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.range.BetweenRangeSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.range.NotBetweenRangeSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.range.RangeSpecification
import br.com.dillmann.dynamicquery.core.specification.filter.unary.*
import br.com.dillmann.dynamicquery.core.specification.group.AndGroupSpecification
import br.com.dillmann.dynamicquery.core.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.core.specification.group.OrGroupSpecification
import io.mockk.mockk
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * [SpecificationFactory] unit tests
 */
class SpecificationFactoryUnitTests {

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
        testBinaryPredicate<EqualsIgnoreCaseBinarySpecification>(PredicateType.EQUALS_IGNORE_CASE)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_EQUALS`() =
        testBinaryPredicate<NotEqualsBinarySpecification>(PredicateType.NOT_EQUALS)

    @Test
    fun `predicate should return the expected specification when asked for a NOT_EQUALS_IGNORE_CASE`() =
        testBinaryPredicate<NotEqualsIgnoreCaseBinarySpecification>(PredicateType.NOT_EQUALS_IGNORE_CASE)

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
        val specification = mockk<Specification>()

        // execution
        val result = SpecificationFactory.negate(specification)

        // validation
        assertEquals(specification, result.negatedExpression)
    }

    @Test
    fun `group should return the expected specification when called with the AND logical operator`() {
        // scenario
        val left = mockk<Specification>()
        val right = mockk<Specification>()

        // execution
        val result = SpecificationFactory.group(LogicalOperatorType.AND, left, right)

        // validation
        assertTrue { result is AndGroupSpecification }
        assertEquals(left, result.leftExpression)
        assertEquals(right, result.rightExpression)
    }

    @Test
    fun `group should return the expected specification when called with the OR logical operator`() {
        // scenario
        val left = mockk<Specification>()
        val right = mockk<Specification>()

        // execution
        val result = SpecificationFactory.group(LogicalOperatorType.OR, left, right)

        // validation
        assertTrue { result is OrGroupSpecification }
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
        val attributeName = randomString
        val arguments = randomListOf(minimumSize = 0, maximumSize = range.first - 1) { randomString }

        // execution
        val result = runCatching { SpecificationFactory.predicate(predicateType, attributeName, arguments) }

        // validation
        assertInvalidArgumentCountException(result, predicateType, attributeName, arguments.size)
    }

    @ParameterizedTest
    @EnumSource(value = PredicateType::class)
    fun `predicate should throw InvalidArgumentCountException when called with more arguments than expected`(
        predicateType: PredicateType,
    ) {
        // scenario
        val attributeName = randomString
        val range = predicateType.argumentCountRange
        val arguments = randomListOf(minimumSize = range.last + 1, maximumSize = range.last + 10) { randomString }

        // execution
        val result = runCatching { SpecificationFactory.predicate(predicateType, attributeName, arguments) }

        // validation
        assertInvalidArgumentCountException(result, predicateType, attributeName, arguments.size)
    }

    private inline fun <reified T: UnarySpecification> testUnaryPredicate(type: PredicateType) {
        // scenario
        val attributeName = randomString

        // execution
        val result = SpecificationFactory.predicate(type, attributeName)

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(attributeName, specification.attributeName)
    }

    private inline fun <reified T: RangeSpecification> testRangePredicate(type: PredicateType) {
        // scenario
        val attributeName = randomString
        val arguments = listOf(randomString, randomString)

        // execution
        val result = SpecificationFactory.predicate(type, attributeName, arguments)

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(attributeName, specification.attributeName)
        assertEquals(arguments.first(), specification.rangeStartValue)
        assertEquals(arguments.last(), specification.rangeEndValue)
    }

    private inline fun <reified T: CollectionSpecification> testCollectionPredicate(type: PredicateType) {
        // scenario
        val attributeName = randomString
        val arguments = randomListOf(minimumSize = 1) { randomString }

        // execution
        val result = SpecificationFactory.predicate(type, attributeName, arguments)

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(attributeName, specification.attributeName)
        assertEquals(arguments, specification.values)
    }

    private inline fun <reified T: BinarySpecification> testBinaryPredicate(type: PredicateType) {
        // scenario
        val attributeName = randomString
        val argument = randomString

        // execution
        val result = SpecificationFactory.predicate(type, attributeName, listOf(argument))

        // validation
        val specification = result as? T
        assertNotNull(specification)
        assertEquals(attributeName, specification.attributeName)
        assertEquals(argument, specification.value)
    }

    private fun assertInvalidArgumentCountException(
        result: Result<*>,
        predicateType: PredicateType,
        attributeName: String,
        argumentCount: Int,
    ) {
        val exception = result.exceptionOrNull() as? InvalidArgumentCountException
        assertNotNull(exception)

        val argumentCountRange = predicateType.argumentCountRange
        assertEquals(attributeName, exception.attributeName)
        assertEquals(predicateType, exception.predicateType)
        assertEquals(argumentCount, exception.currentArgumentCount)
        assertEquals(argumentCountRange.first, exception.minimumArgumentCount)
        assertEquals(argumentCountRange.last, exception.maximumArgumentCount)
    }

}
