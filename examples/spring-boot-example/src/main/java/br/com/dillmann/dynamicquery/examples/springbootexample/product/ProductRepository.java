package br.com.dillmann.dynamicquery.examples.springbootexample.product;

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification;
import br.com.dillmann.dynamicquery.springboot.datajpa.DynamicQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, DynamicQueryRepository<Product> {

    default Page<Product> findActiveProducts(DynamicQuerySpecification dynamicQuery, Pageable pageable) {
        final Specification<Product> activeFilter =
            (root, query, builder) -> builder.equal(root.get("active"), true);

        if (dynamicQuery == null)
            return findAll(activeFilter, pageable);
        else
            return findAll(dynamicQuery, pageable, query -> query.and(activeFilter));
    }
}
