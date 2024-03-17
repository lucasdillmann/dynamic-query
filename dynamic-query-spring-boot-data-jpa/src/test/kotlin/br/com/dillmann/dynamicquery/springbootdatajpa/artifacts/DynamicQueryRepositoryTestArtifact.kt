package br.com.dillmann.dynamicquery.springbootdatajpa.artifacts

import br.com.dillmann.dynamicquery.springbootdatajpa.DynamicQueryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.FluentQuery
import java.util.*
import java.util.function.Function

/**
 * Test artifact for the [DynamicQueryRepository] with a mocked implementation of all Spring native portion of the
 * interface (functions inherited from [JpaSpecificationExecutor])
 */
class DynamicQueryRepositoryTestArtifact(
    private val findAllListDelegate: (Specification<Any>, Sort?) -> List<Any>,
    private val findAllPageDelegate: (Specification<Any>, Pageable) -> Page<Any>,
    private val findOneDelegate: (Specification<Any>) -> Optional<Any>,
    private val countDelegate: (Specification<Any>) -> Long,
    private val existsDelegate: (Specification<Any>) -> Boolean,
    private val deleteDelegate: (Specification<Any>) -> Long,
): DynamicQueryRepository<Any> {

    override fun findAll(specification: Specification<Any>): List<Any> =
        findAllListDelegate(specification, null)

    override fun findAll(specification: Specification<Any>, pageable: Pageable): Page<Any> =
        findAllPageDelegate(specification, pageable)

    override fun findAll(specification: Specification<Any>, sort: Sort): List<Any> =
        findAllListDelegate(specification, sort)

    override fun findOne(specification: Specification<Any>): Optional<Any> =
        findOneDelegate(specification)

    override fun count(specification: Specification<Any>): Long =
        countDelegate(specification)

    override fun exists(specification: Specification<Any>): Boolean =
        existsDelegate(specification)

    override fun delete(specification: Specification<Any>): Long =
        deleteDelegate(specification)

    override fun <S : Any, R : Any> findBy(
        specification: Specification<Any>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R = error("Not supported")
}
