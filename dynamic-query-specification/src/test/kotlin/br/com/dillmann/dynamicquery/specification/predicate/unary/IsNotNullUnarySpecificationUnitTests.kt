package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.randomString
import br.com.dillmann.dynamicquery.specification.path.PathResolver
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.*
import kotlin.test.assertEquals

/**
 * [IsNotNullUnarySpecification] unit tests
 */
class IsNotNullUnarySpecificationUnitTests {

    private val attributeName = randomString
    private val path = mockk<Path<Any>>()
    private val predicate = mockk<Predicate>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val specification = IsNotNullUnarySpecification(attributeName)

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mockkObject(PathResolver)
            mockkStatic(PathResolver::class)
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
        every { builder.isNotNull(any<Path<Any>>()) } returns predicate
    }

    @Test
    fun `toPredicate should resolve and use the target using PathResolver`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { PathResolver.resolve(attributeName, root) }
        verify { builder.isNotNull(path) }
    }

    @Test
    fun `toPredicate should return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
