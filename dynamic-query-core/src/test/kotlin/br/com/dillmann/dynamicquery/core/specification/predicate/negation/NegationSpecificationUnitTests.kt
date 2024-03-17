package br.com.dillmann.dynamicquery.core.specification.predicate.negation

import br.com.dillmann.dynamicquery.core.specification.Specification
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import kotlin.test.assertEquals

/**
 * [NegationSpecification] unit tests
 */
class NegationSpecificationUnitTests {

    private val predicate = mockk<Predicate>()
    private val negatedPredicate = mockk<Predicate>()
    private val root = mockk<Root<Any>>()
    private val query = mockk<CriteriaQuery<Any>>()
    private val builder = mockk<CriteriaBuilder>()
    private val childSpecification = mockk<Specification>()
    private val specification = NegationSpecification(childSpecification)

    @BeforeEach
    fun setUp() {
        every { childSpecification.toPredicate(root, query, builder) } returns predicate
        every { predicate.not() } returns negatedPredicate
    }

    @Test
    fun `toPredicate should evaluate the child specification with the expected values`() {
        // execution
        specification.toPredicate(root, query, builder)

        // validation
        verify { childSpecification.toPredicate(root, query, builder) }
    }

    @Test
    fun `toPredicate should return the negation of the predicate provided by the child specification`() {
        // execution
        val result = specification.toPredicate(root, query, builder)

        // validation
        assertEquals(negatedPredicate, result)
        verify { predicate.not() }
    }
}