package br.com.dillmann.dynamicquery.springbootdatajpa

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import br.com.dillmann.dynamicquery.springbootdatajpa.artifacts.DynamicQueryRepositoryTestArtifact
import br.com.dillmann.dynamicquery.springbootdatajpa.artifacts.randomBoolean
import br.com.dillmann.dynamicquery.springbootdatajpa.artifacts.randomLong
import io.mockk.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import java.util.*
import kotlin.reflect.KFunction

/**
 * [DynamicQueryRepository] unit tests
 */
class DynamicQueryRepositoryUnitTests {

    private val findAllListMock: (Specification<Any>, Sort?) -> List<Any> = mockk()
    private val findAllPageMock: (Specification<Any>, Pageable) -> Page<Any> = mockk()
    private val findOneMock: (Specification<Any>) -> Optional<Any> = mockk()
    private val countMock: (Specification<Any>) -> Long = mockk()
    private val existsMock: (Specification<Any>) -> Boolean = mockk()
    private val deleteMock: (Specification<Any>) -> Long = mockk()
    private val scopeDownSupplier: (Specification<Any>) -> Specification<Any> = mockk()
    private val specification: DynamicQuerySpecification = mockk()
    private val page: Pageable = mockk()
    private val sorting: Sort = mockk()

    private val repository = DynamicQueryRepositoryTestArtifact(
        findAllListMock,
        findAllPageMock,
        findOneMock,
        countMock,
        existsMock,
        deleteMock,
    )

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            val toSpringSpecification: DynamicQuerySpecification.() -> Specification<Any> =
                DynamicQuerySpecification::toSpringSpecification
            val toSpringSpecificationWithScopeDown: DynamicQuerySpecification.(ScopeDownSupplier<Any>) -> Specification<Any> =
                DynamicQuerySpecification::toSpringSpecification

            mockkStatic(
                toSpringSpecification as KFunction<*>,
                toSpringSpecificationWithScopeDown as KFunction<*>,
            )
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            unmockkAll()
        }
    }

    @BeforeEach
    fun setUp() {
        every { findAllListMock(any(), any()) } returns emptyList()
        every { findAllPageMock(any(), any()) } returns Page.empty()
        every { findOneMock(any()) } returns Optional.empty()
        every { countMock(any()) } returns randomLong
        every { existsMock(any()) } returns randomBoolean
        every { deleteMock(any()) } returns randomLong
        every { scopeDownSupplier(any()) } answers { arg(0) }
    }

    @Test
    fun `findAll for a list with no scope-down customizer and no sorting should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { findAllListMock(any<DynamicQuerySpecificationAdapter<Any>>(), null) }
    }

    @Test
    fun `findAll for a list with a scope-down customizer and no sorting should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { findAllListMock(any<DynamicQuerySpecificationAdapter<Any>>(), null) }
    }

    @Test
    fun `findAll for a list with no scope-down customizer and a sorting should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification, sorting)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { findAllListMock(any<DynamicQuerySpecificationAdapter<Any>>(), sorting) }
    }

    @Test
    fun `findAll for a list with a scope-down customizer and a sorting should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification, sorting, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { findAllListMock(any<DynamicQuerySpecificationAdapter<Any>>(), sorting) }
    }

    @Test
    fun `findAll for a page with no scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification, page)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { findAllPageMock(any<DynamicQuerySpecificationAdapter<Any>>(), page) }
    }

    @Test
    fun `findAll for a page with a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findAll(specification, page, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { findAllPageMock(any<DynamicQuerySpecificationAdapter<Any>>(), page) }
    }

    @Test
    fun `findOne without a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findOne(specification)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { findOneMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `findOne with a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.findOne(specification, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { findOneMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `count without a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.count(specification)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { countMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `count with a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.count(specification, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { countMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `exists without a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.exists(specification)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { existsMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `exists with a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.exists(specification, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { existsMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `delete without a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.delete(specification)

        // validation
        verify { specification.toSpringSpecification<Any>() }
        verify { deleteMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }

    @Test
    fun `delete with a scope-down customizer should convert the specification and delegate the execution to the Spring Data`() {
        // execution
        repository.delete(specification, scopeDownSupplier)

        // validation
        verify { specification.toSpringSpecification(scopeDownSupplier) }
        verify { deleteMock(any<DynamicQuerySpecificationAdapter<Any>>()) }
    }
}
