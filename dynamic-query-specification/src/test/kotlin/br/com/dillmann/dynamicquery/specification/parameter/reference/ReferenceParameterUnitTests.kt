package br.com.dillmann.dynamicquery.specification.parameter.reference

import br.com.dillmann.dynamicquery.specification.path.PathConverters
import br.com.dillmann.dynamicquery.specification.path.PathResolver
import br.com.dillmann.dynamicquery.specification.randomString
import io.mockk.*
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [ReferenceParameter] unit tests
 */
class ReferenceParameterUnitTests {

    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val path = mockk<Path<Any>>()

    @BeforeEach
    fun setUp() {
        mockkObject(PathConverters, PathResolver)
        mockkStatic(PathConverters::class, PathResolver::class)

        every { PathResolver.resolve(any(), any()) } returns path
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `asExpression should convert the attribute name and resolve the conversion result to a JPA Path`() {
        // scenario
        val inputValue = randomString
        val convertedValue = randomString
        every { PathConverters.convert(any(), any()) } returns convertedValue

        // execution
        val output = ReferenceParameter(inputValue).asExpression(root, query, builder)

        // validation
        verify { PathConverters.convert(inputValue, root) }
        verify { PathResolver.resolve(convertedValue, root) }
        assertEquals(path, output)
    }

}
