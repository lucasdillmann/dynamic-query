package br.com.dillmann.dynamicquery.specification.predicate.collection

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import br.com.dillmann.dynamicquery.specification.randomListOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import jakarta.persistence.criteria.CriteriaBuilder.In
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [InCollectionSpecification] unit tests
 */
class InCollectionSpecificationUnitTests {

    private val attributeName = mockk<Parameter>()
    private val values = randomListOf<Parameter>(minimumSize = 1) {
        mockk {
            every { asExpression(any(), any(), any(), any<Class<*>>()) } returns mockk()
        }
    }
    private val path = mockk<Expression<String>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<In<Any>>()
    private val specification = InCollectionSpecification(attributeName, values)

    @BeforeEach
    fun setUp() {
        every { attributeName.asExpression(any(), any(), any()) } returns path
        every { builder.`in`(any<Expression<Any>>()) } returns predicate
        every { predicate.value(any<Expression<String>>()) } returns predicate
        every { predicate.value(any<String>()) } returns predicate
        every { path.javaType } returns String::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeName.asExpression(root, query, builder) }
        verify { builder.`in`(path) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using ValueParsers`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        values.forEach { value ->
            verify { value.asExpression(root, query, builder, String::class.java) }
        }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
        verify { builder.`in`(any<Expression<String>>()) }
    }
}
