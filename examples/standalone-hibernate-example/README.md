# Standalone Hibernate example for the Dynamic Query

This source code is a simple example of the integration and use of the Dynamic Query for JPA in a standalone
Hibernate-based project (or other JPA-compliant ORM). Only note that this is only a simple example, it isn't good 
enough to be used as a template for new projects.

Some key files to check out in this source:
- `ProductGroupHttpHandler`: Simple example on how to use the `DynamicQuery.parse()` API to parse a query string
  and apply it as a JPA Criteria predicate
- `ProductHttpHandler`: More advanced use of the `DynamicQuery` with a simple example on how to apply fixed query
  conditions on top of the Dynamic Query ones
- `Application`: Sets up a JDK `HttpServer` and wires everything together

To get started with the demo, just execute `./gradlew run` on the command line and make some requests. The source
code uses an embedded H2 in-memory database with some pre-filled data.

Some request examples:

- [GET /product-groups?query=like(description, "Fruit%")](http://localhost:8080/product-groups?query=like%28description%2C%20%22Fruit%25%22%29)
  to return only the product groups whose description starts with "Fruit"
- [GET /products?query=lessThan(unitPrice, 1)](http://localhost:8080/products?query=lessThan%28unitPrice%2C%201%29)
  to retrieve the active products with a unit price less than 1
- [GET /products?query=like(description, "B%")](http://localhost:8080/products?query=like%28description%2C%20%22B%25%22%29)
  to retrieve the active products whose description starts with "B"
- [GET /products?query=equals(productGroup.id, 1)](http://localhost:8080/products?query=equals%28productGroup.id%2C%201%29)
  to retrieve the active products belonging to product group 1
