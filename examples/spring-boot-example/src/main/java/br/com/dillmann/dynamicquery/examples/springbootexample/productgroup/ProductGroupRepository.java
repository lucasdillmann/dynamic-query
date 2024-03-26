package br.com.dillmann.dynamicquery.examples.springbootexample.productgroup;

import br.com.dillmann.dynamicquery.springboot.datajpa.DynamicQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroupRepository
    extends JpaRepository<ProductGroup, Integer>, DynamicQueryRepository<ProductGroup> {
}
