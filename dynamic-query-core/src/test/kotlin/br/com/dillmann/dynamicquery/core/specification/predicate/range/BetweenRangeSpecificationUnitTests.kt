package br.com.dillmann.dynamicquery.core.specification.predicate.range

import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.specification.path.PathResolver
import br.com.dillmann.dynamicquery.core.valueparser.ValueParsers
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.*
import kotlin.test.assertEquals

/**
 * [BetweenRangeSpecification] unit tests
 */
class BetweenRangeSpecificationUnitTests {

    private val attributeName = randomString
    private val startValue = randomString
    private val endValue = randomString
    private val path = mockk<Path<String>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val specification = BetweenRangeSpecification(attributeName, startValue, endValue)

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
        every { path.javaType } returns String::class.java
        every { builder.between(any<Expression<String>>(), any<String>(), any<String>()) } returns predicate
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { PathResolver.resolve(attributeName, root) }
        verify { builder.between(path, any<String>(), any<String>()) }
    }

    @Test
    fun `toPredicate should parse the start value to the target type using ValueParsers`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { ValueParsers.parse(startValue, String::class.java) }
        verify { builder.between(any(), startValue, any()) }
    }

    @Test
    fun `toPredicate should parse the end value to the target type using ValueParsers`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { ValueParsers.parse(endValue, String::class.java) }
        verify { builder.between(any(), any(), endValue) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
