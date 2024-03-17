package br.com.dillmann.dynamicquery.core.specification.path

import br.com.dillmann.dynamicquery.core.randomString
import br.com.dillmann.dynamicquery.core.specification.exception.UnknownAttributeNameException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.persistence.criteria.From
import javax.persistence.criteria.Join
import javax.persistence.criteria.Path
import javax.persistence.metamodel.Bindable
import kotlin.test.assertEquals

/**
 * [PathResolver] unit tests
 */
class PathResolverUnitTests {

    private val root = mockk<From<Any, Any>>()
    private val simpleAttribute = mockk<Path<Any>>()
    private val joinAttribute = mockk<Join<Any, Any>>()
    private val joinChildSimpleAttribute = mockk<Path<Any>>()
    private val model = mockk<Bindable<Any>>()

    @BeforeEach
    fun setUp() {
        every { simpleAttribute.model } returns model
        every { joinChildSimpleAttribute.model } returns model
        every { joinAttribute.model } returns null
        every { joinAttribute.get<Any>("simpleAttribute") } returns joinChildSimpleAttribute
        every { root.get<Any>(any<String>()) } throws IllegalArgumentException()
        every { root.get<Any>("simpleAttribute") } returns simpleAttribute
        every { root.get<Any>("joinAttribute") } returns joinAttribute
        every { root.join<Any, Any>("joinAttribute") } returns joinAttribute
    }

    @Test
    fun `resolve should return the expected path when the requested value is a direct attribute`() {
        // execution
        val result = PathResolver.resolve("simpleAttribute", root)

        // validation
        assertEquals(simpleAttribute, result)
    }

    @Test
    fun `resolve should return the expected path when the requested value is within a join expression`() {
        // execution
        val result = PathResolver.resolve("joinAttribute.simpleAttribute", root)

        // validation
        assertEquals(joinChildSimpleAttribute, result)
    }

    @Test
    fun `resolve should throw UnknownAttributeNameException when the requested value doesn't exist in the tree`() {
        // validation
        assertThrows<UnknownAttributeNameException> {
            // execution
            PathResolver.resolve(randomString, root)
        }
    }
}
