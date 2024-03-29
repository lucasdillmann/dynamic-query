package br.com.dillmann.dynamicquery.specification.predicate.collection

import br.com.dillmann.dynamicquery.specification.randomListOf
import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.path.PathResolver
import br.com.dillmann.dynamicquery.specification.valueparser.ValueParsers
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.*
import jakarta.persistence.criteria.CriteriaBuilder.In
import kotlin.test.assertEquals

/**
 * [InCollectionSpecification] unit tests
 */
class InCollectionSpecificationUnitTests {

    private val attributeName = randomString
    private val values = randomListOf(minimumSize = 1) { randomString }
    private val path = mockk<Path<String>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<In<Any>>()
    private val specification = InCollectionSpecification(attributeName, values)

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkObject(PathResolver, ValueParsers)
            mockkStatic(PathResolver::class, ValueParsers::class)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { PathResolver.resolve(any(), any()) } returns path
        every { ValueParsers.parse<String>(any(), any()) } answers { arg<String>(0) }
        every { builder.`in`<Any>(any()) } returns predicate
        every { predicate.value(any<String>()) } returns predicate
        every { path.javaType } returns String::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { PathResolver.resolve(attributeName, root) }
        verify { builder.`in`(path) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using ValueParsers`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        values.forEach { value ->
            verify { ValueParsers.parse(value, String::class.java) }
            verify { predicate.value(value) }
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
