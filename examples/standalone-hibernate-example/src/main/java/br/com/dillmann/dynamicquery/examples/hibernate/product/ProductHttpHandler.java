package br.com.dillmann.dynamicquery.examples.hibernate.product;

import br.com.dillmann.dynamicquery.examples.hibernate.AbstractHttpHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductHttpHandler extends AbstractHttpHandler<Product> {
    public ProductHttpHandler(final EntityManager entityManager) {
        super(entityManager, Product.class);
    }

    @Override
    protected Predicate staticPredicate(
        final Root<Product> root,
        final CriteriaQuery<Product> query,
        final CriteriaBuilder builder
    ) {
        return builder.equal(root.get("active"), true);
    }
}
