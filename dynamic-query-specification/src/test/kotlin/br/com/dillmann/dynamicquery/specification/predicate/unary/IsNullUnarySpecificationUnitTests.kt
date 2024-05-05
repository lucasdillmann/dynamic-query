package br.com.dillmann.dynamicquery.specification.predicate.unary

import br.com.dillmann.dynamicquery.specification.parameter.Parameter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.criteria.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [IsNullUnarySpecification] unit tests
 */
class IsNullUnarySpecificationUnitTests {

    private val attributeProvider = mockk<Parameter>()
    private val path = mockk<Expression<Any>>()
    private val predicate = mockk<Predicate>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val specification = IsNullUnarySpecification(attributeProvider)

    @BeforeEach
    fun setUp() {
        every { builder.isNull(any<Expression<Any>>()) } returns predicate
        every { attributeProvider.asExpression(any(), any(), any()) } returns path
    }

    @Test
    fun `toPredicate should resolve and use the target using the provided Parameter`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { attributeProvider.asExpression(root, query, builder) }
        verify { builder.isNull(path) }
    }

    @Test
    fun `toPredicate should return the expected predicate`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(predicate, result)
    }
}
