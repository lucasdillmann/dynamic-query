# Spring Boot example for the Dynamic Query

This source code is a simple example of the integration and use of the Dynamic Query for JPA in a Spring Boot-based
project. Only note that this is only a simple example, he isn't good enough to be used as a template for new projects.

Some key files to check out in this source:
- `ProductGroupController`: Simple example on how to use Spring injection to receive an instance of the 
  `DynamicQuerySpecification`, automatically parsed using the value available in the request's `query` query parameter
- `ProductRepository`: More advanced use of the `DynamicQueryRepository` with a simple example on how to apply
  fixed query conditions on top of the Dynamic Query ones

To get started with the demo, just execute `./gradlew bootRun` on the command line and make some requests. The source
code uses an embedded H2 in-memory database with some pre-filled data.

Some request examples:

- [GET /products?query=equals(productGroup.id, 5)](http://localhost:8080/products?query=equals%28productGroup.id%2C%205%29) 
  to return only the products associated with the group of ID 5
- [GET /products?query=equals(productGroup.id, 1)||lessThan(unitPrice, 1)](http://localhost:8080/products?query=equals%28productGroup.id%2C%201%29%7C%7ClessThan%28unitPrice%2C%201%29) 
  to retrieve the products that either belong to the group with ID 1 or have a unit price less than one
