package br.com.dillmann.dynamicquery.examples.springbootexample.product;

import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    @Autowired
    public ProductController(final ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProductGroups(
        final Pageable page,
        final DynamicQuerySpecification dynamicQuery
    ) {
        final Page<Product> results = repository.findActiveProducts(dynamicQuery, page);
        return ResponseEntity.ok(results);
    }
}
