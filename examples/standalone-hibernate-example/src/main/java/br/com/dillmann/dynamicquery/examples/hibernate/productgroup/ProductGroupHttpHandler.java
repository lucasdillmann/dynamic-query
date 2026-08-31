package br.com.dillmann.dynamicquery.examples.hibernate.productgroup;

import br.com.dillmann.dynamicquery.examples.hibernate.AbstractHttpHandler;
import jakarta.persistence.EntityManager;

public class ProductGroupHttpHandler extends AbstractHttpHandler<ProductGroup> {
    public ProductGroupHttpHandler(final EntityManager entityManager) {
        super(entityManager, ProductGroup.class);
    }
}
