package br.com.dillmann.dynamicquery.core.specification.group

import br.com.dillmann.dynamicquery.core.specification.Specification
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import jakarta.persistence.criteria.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * [OrGroupSpecification] unit tests
 */
class OrGroupSpecificationUnitTests {

    private val root = mockk<Root<*>>()
    private val query = mockk<CriteriaQuery<*>>()
    private val builder = mockk<CriteriaBuilder>()
    private val leftSpecification = mockk<Specification>()
    private val rightSpecification = mockk<Specification>()
    private val leftPredicate = mockk<Predicate>()
    private val rightPredicate = mockk<Predicate>()
    private val orPredicate = mockk<Predicate>()
    private val groupSpecification = OrGroupSpecification(leftSpecification, rightSpecification)

    @BeforeEach
    fun setUp() {
        every { leftSpecification.toPredicate(any(), any(), any()) } returns leftPredicate
        every { rightSpecification.toPredicate(any(), any(), any()) } returns rightPredicate
        every { builder.or(any<Expression<Boolean>>(), any<Expression<Boolean>>()) } returns orPredicate
    }

    @Test
    fun `toPredicate should execute both left and right specifications with the expected arguments`() {
        // execution
        groupSpecification.toPredicate(root, query, builder)

        // validation
        verify { leftSpecification.toPredicate(root, query, builder) }
        verify { rightSpecification.toPredicate(root, query, builder) }
    }

    @Test
    fun `toPredicate should call the CriteriaBuilder's and operation with the expected values and return its result`() {
        // execution
        val result = groupSpecification.toPredicate(root, query, builder)

        // validation
        assertEquals(orPredicate, result)
        verify {
            builder.or(leftPredicate as Expression<Boolean>, rightPredicate as Expression<Boolean>)
        }
    }

}
