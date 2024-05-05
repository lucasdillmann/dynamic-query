package br.com.dillmann.dynamicquery.specification.parameter.literal

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import io.mockk.*
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [LiteralParameter] unit tests
 */
class LiteralParameterUnitTests {

    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val nullLiteralExpression = mockk<Expression<String>>()
    private val literalExpression = mockk<Expression<String>>()

    @BeforeEach
    fun setUp() {
        mockkObject(ValueParsers)
        mockkStatic(ValueParsers::class)

        every { builder.nullLiteral<String>(any()) } returns nullLiteralExpression
        every { builder.literal<String>(any()) } returns literalExpression
        every { ValueParsers.parse<Any>(any(), any()) } returns mockk()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `asExpression should return a null literal when the input value is null`() {
        // execution
        val result = LiteralParameter(null).asExpression(root, query, builder)

        // validation
        assertEquals(result, nullLiteralExpression)
        verify { builder.nullLiteral(Any::class.java) }
    }

    @Test
    fun `asExpression should return a literal value without parsing it when the target type is null`() {
        // scenario
        val value = randomString

        // execution
        val result = LiteralParameter(value).asExpression<String>(root, query, builder, null)

        // validation
        assertEquals(result, literalExpression)
        verify { builder.literal(value) }
        verify { ValueParsers.parse<Any>(any(), any()) wasNot Called }
    }

    @Test
    fun `asExpression should return a literal parsed value when the target type was informed`() {
        // scenario
        val inputValue = randomString
        val parsedValue = randomString
        every { ValueParsers.parse<String>(any(), any()) } returns parsedValue

        // execution
        val result = LiteralParameter(inputValue).asExpression(root, query, builder, String::class.java)

        // validation
        assertEquals(result, literalExpression)
        verify { ValueParsers.parse(inputValue, String::class.java) }
        verify { builder.literal(parsedValue) }
    }

}
