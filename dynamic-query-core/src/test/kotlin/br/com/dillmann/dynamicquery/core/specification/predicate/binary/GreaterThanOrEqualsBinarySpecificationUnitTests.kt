package br.com.dillmann.dynamicquery.core.specification.predicate.binary

import br.com.dillmann.dynamicquery.core.randomLong
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
 * [GreaterThanOrEqualsBinarySpecification] unit tests
 */
class GreaterThanOrEqualsBinarySpecificationUnitTests {

    private val attributeName = randomString
    private val value = randomString
    private val path = mockk<Path<Long>>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val predicate = mockk<Predicate>()
    private val specification = GreaterThanOrEqualsBinarySpecification(attributeName, value)

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
        every { ValueParsers.parse<Long>(any(), any()) } returns randomLong
        every { builder.greaterThanOrEqualTo(any<Expression<Long>>(), any<Long>()) } returns predicate
        every { path.javaType } returns Long::class.java
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { PathResolver.resolve(attributeName, root) }
        verify { builder.greaterThanOrEqualTo(path, any<Long>()) }
    }

    @Test
    fun `toPredicate should parse the value to the target type using ValueParsers`() {
        // scenario
        val expectedValue = randomString
        every { ValueParsers.parse<Any>(any(), any()) } returns expectedValue

        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { ValueParsers.parse(value, Long::class.java) }
        verify { builder.greaterThanOrEqualTo(any(), expectedValue) }
    }

    @Test
    fun `toPredicate should create and return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
        verify { builder.greaterThanOrEqualTo(any<Expression<Long>>(), any<Long>()) }
    }
}
