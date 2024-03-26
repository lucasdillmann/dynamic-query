package br.com.dillmann.dynamicquery.examples.springbootexample.productgroup;

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-groups")
public class ProductGroupController {

    private final ProductGroupRepository repository;

    @Autowired
    public ProductGroupController(final ProductGroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Page<ProductGroup>> getProductGroups(
        final Pageable page,
        final DynamicQuerySpecification dynamicQuery
    ) {
        final Page<ProductGroup> results = dynamicQuery != null
            ? repository.findAll(dynamicQuery, page)
            : repository.findAll(page);

        return ResponseEntity.ok(results);
    }
}
