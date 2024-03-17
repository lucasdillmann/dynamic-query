package br.com.dillmann.dynamicquery.core.valueparser

import br.com.dillmann.dynamicquery.core.randomBoolean
import br.com.dillmann.dynamicquery.core.randomInt
import br.com.dillmann.dynamicquery.core.randomLong
import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.valueparser.default.*
import br.com.dillmann.dynamicquery.core.valueparser.exception.NoValueParserAvailableException
import br.com.dillmann.dynamicquery.core.valueparser.exception.ParseFailedException
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.time.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * [ValueParsers] unit tests
 */
class ValueParsersUnitTests {

    class TestType
    private val testValue = mockk<TestType>()
    private val testParser = mockk<ValueParser<TestType>>()

    @BeforeEach
    fun setUp() {
        every { testParser.priority } returns Int.MIN_VALUE
        every { testParser.supports(any()) } answers { TestType::class.java.isAssignableFrom(arg(0)) }
        every { testParser.parse(any()) } returns testValue

        ValueParsers.register(testParser)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
        ValueParsers.reset()
    }

    @Test
    fun `parse should throw NoValueParserAvailableException when no parser supports the requested target type`() {
        // scenario
        class UnsupportedTestType
        val inputValue = randomString

        // execution
        val result = runCatching { ValueParsers.parse(inputValue, UnsupportedTestType::class.java) }

        // validation
        val exception = result.exceptionOrNull() as? NoValueParserAvailableException
        assertNotNull(exception)
        assertEquals(inputValue, exception.value)
        assertEquals(UnsupportedTestType::class.java, exception.targetType)
    }

    @Test
    fun `parse should throw ParseFailedException when the resolved parser fails with an exception`() {
        // scenario
        val rootException = RuntimeException(randomString)
        val inputValue = randomString
        every { testParser.parse(any()) } throws rootException

        // execution
        val result = runCatching { ValueParsers.parse(inputValue, TestType::class.java) }

        // validation
        val exception = result.exceptionOrNull() as? ParseFailedException
        assertNotNull(exception)
        assertEquals(inputValue, exception.value)
        assertEquals(TestType::class.java, exception.targetType)
        assertEquals(rootException, exception.cause)
    }

    @Test
    fun `parse should call and return the value provided by the parser`() {
        // scenario
        val inputValue = randomString

        // execution
        val result = ValueParsers.parse(inputValue, TestType::class.java)

        // validation
        assertEquals(testValue, result)
        verify { testParser.parse(inputValue) }
    }

    @Test
    fun `parse should only invoke the parser if it supports the requested target type`() {
        // scenario
        val parser1 = mockk<ValueParser<Any>>()
        val parser2 = mockk<ValueParser<Any>>()
        every { parser1.supports(any()) } returns false
        every { parser2.supports(any()) } returns true
        every { parser2.parse(any()) } returns mockk()
        every { parser1.priority } returns Int.MIN_VALUE
        every { parser2.priority } returns Int.MIN_VALUE + 1

        ValueParsers.register(parser1)
        ValueParsers.register(parser2)

        // execution
        ValueParsers.parse(randomString, Any::class.java)

        // validation
        verify(exactly = 0) { parser1.parse(any()) }
        verify { parser2.parse(any()) }
    }

    @Test
    fun `parse should only invoke the parser with the higher priority that supports the type`() {
        // scenario
        val parser1 = mockk<ValueParser<Any>>()
        val parser2 = mockk<ValueParser<Any>>()
        every { parser1.supports(any()) } returns true
        every { parser2.supports(any()) } returns true
        every { parser1.parse(any()) } returns mockk()
        every { parser1.priority } returns Int.MIN_VALUE
        every { parser2.priority } returns Int.MIN_VALUE + 1

        ValueParsers.register(parser1)
        ValueParsers.register(parser2)

        // execution
        ValueParsers.parse(randomString, Any::class.java)

        // validation
        verify { parser1.parse(any()) }
        verify(exactly = 0) { parser2.parse(any()) }
    }

    @Test
    fun `reset should remove any previously installed custom parser`() {
        // scenario
        val parser = mockk<ValueParser<String>>()
        every { parser.priority } returns Int.MIN_VALUE
        ValueParsers.register(parser)

        // execution
        ValueParsers.reset()

        // validation
        ValueParsers.parse(randomString, String::class.java)
        verify(exactly = 0) { parser.parse(any()) }
    }

    @Test
    fun `parse should support BigDecimal values by default using BigDecimalValueParser`() =
        testDefaultParser(BigDecimal.valueOf(randomLong)) { BigDecimalValueParser }

    @Test
    fun `parse should support BigInteger values by default using BigIntegerValueParser`() =
        testDefaultParser(BigInteger.valueOf(randomLong)) { BigIntegerValueParser }

    @Test
    fun `parse should support Boolean values by default using BooleanValueParser`() =
        testDefaultParser(randomBoolean) { BooleanValueParser }

    @Test
    fun `parse should support Byte values by default using ByteValueParser`() =
        testDefaultParser((Byte.MIN_VALUE..Byte.MAX_VALUE).random().toByte()) { ByteValueParser }

    @Test
    fun `parse should support CharArray values by default using CharArrayValueParser`() =
        testDefaultParser(randomString.toCharArray()) { CharArrayValueParser }

    @Test
    fun `parse should support Double values by default using DoubleValueParser`() =
        testDefaultParser(randomInt.toDouble()) { DoubleValueParser }

    @Test
    fun `parse should support Float values by default using FloatValueParser`() =
        testDefaultParser(randomInt.toFloat()) { FloatValueParser }

    @Test
    fun `parse should support Int values by default using IntValueParser`() =
        testDefaultParser(randomInt) { IntValueParser }

    @Test
    fun `parse should support LocalDateTime values by default using LocalDateTimeValueParser`() =
        testDefaultParser(LocalDateTime.now()) { LocalDateTimeValueParser }

    @Test
    fun `parse should support LocalDate values by default using LocalDateValueParser`() =
        testDefaultParser(LocalDate.now()) { LocalDateValueParser }

    @Test
    fun `parse should support LocalTime values by default using LocalTimeValueParser`() =
        testDefaultParser(LocalTime.now()) { LocalTimeValueParser }

    @Test
    fun `parse should support Long values by default using LongValueParser`() =
        testDefaultParser(randomLong) { LongValueParser }

    @Test
    fun `parse should support OffsetDateTime values by default using OffsetDateTimeValueParser`() =
        testDefaultParser(OffsetDateTime.now()) { OffsetDateTimeValueParser }

    @Test
    fun `parse should support OffsetTime values by default using OffsetTimeValueParser`() =
        testDefaultParser(OffsetTime.now()) { OffsetTimeValueParser }

    @Test
    fun `parse should support Short values by default using ShortValueParser`() =
        testDefaultParser((Short.MIN_VALUE..Short.MAX_VALUE).random().toShort()) { ShortValueParser }

    @Test
    fun `parse should support String values by default using StringValueParser`() =
        testDefaultParser(randomString) { StringValueParser }

    @Test
    fun `parse should support UUID values by default using UuidValueParser`() =
        testDefaultParser(UUID.randomUUID()) { UuidValueParser }

    @Test
    fun `parse should support ZonedDateTime values by default using ZonedDateTimeValueParser`() =
        testDefaultParser(ZonedDateTime.now()) { ZonedDateTimeValueParser }

    private inline fun <reified T: Any> testDefaultParser(sample: T, crossinline parserProvider: () -> ValueParser<T>) {
        // scenario
        val inputValue = randomString

        ValueParsers.deregister(parserProvider()) // removes the concrete implementation before mocking it
        mockkObject(parserProvider())
        ValueParsers.register(parserProvider()) // installs the mocked instance
        every { parserProvider().parse(any()) } returns sample
        every { parserProvider().supports(any()) } answers { arg<Class<*>>(0) == T::class.java }
        every { parserProvider().priority } returns Int.MAX_VALUE

        // execution
        val result = ValueParsers.parse(inputValue, T::class.java)

        // validation
        verify { parserProvider().parse(inputValue) }
        assertEquals(sample, result)
    }
}
