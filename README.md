# Dynamic Query for JPA

This project provides a simple and easy to use DSL for dynamic queries backed by a JPA-compliant ORM, like Hibernate.
Main goal is to enable a simple way for applications, mainly REST APIs, to have the possibility to apply dynamic
filters in the data that the application manages without the need to hardcode every single business scenario or search
possibility.

## Getting started

To get started, you'll need to include the dynamic-query-core module in your application (via Maven, Gradle, sbt, etc).
Dynamic Query isn't available through Maven Central yet, but the publication is in the works.

Once included in your classpath, you can use the `DynamicQuery` class to parse any expression formatted as a `String`
into a JPA-compliant specification, which can be used to filter the query results. The following code exemplifies the
process of parsing and using the specification in a JPA query.

```java
// Initialize the query using JPA Criteria APIs
final EntityManager entityManager = Persistence.createEntityManagerFactory("example").createEntityManager();
final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
final CriteriaQuery<EntityClass> criteriaQuery = builder.createQuery(EntityClass.class);
final Root<EntityClass> root = criteriaQuery.getRoots().iterator().next();

// Parses a dynamic expression using Dynamic Query to a JPA Predicate
// This portion is the only code that goes beyond your (probably) regular JPA query code
final String expression = "equals(active, true)||isNull(active)";
final DynamicQuerySpecification specification = DynamicQuery.parse(expression);
final Predicate predicate = specification.toPredicate(root, criteriaQuery, builder);

// Apply the predicate and retrieve the query results
final TypedQuery<EntityClass> query = entityManager.createQuery(criteriaQuery.where(predicate));
final List<EntityClass> queryResults = query.getResultList();
```

If you're using Spring Boot, check the Getting Started section of the
[`spring-boot-data-jpa`](dynamic-query-spring-boot-data-jpa/README.md) and
[`spring-boot-web`](dynamic-query-spring-boot-web/README.md) modules. They provide a simplified way to integrate and
use Dynamic Query with the framework as if Dynamic Query was a native resource.

### Examples

If you is a developer that learns quickly by example, check-out the [`examples`](examples/) folder. There's some
example projects there with the Dynamic Query integrated for you. Just note that such projects are for demo purposes
only, they aren't intended to be used as a template or something like that.

## Building a Dynamic Query expression

A Dynamic Query expression is, at its core, a set of instructions that will be applied to a query as the conditions
that the data must met. A expression is a compound of one or more operations and logic operators (AND/OR) between
them.

In general, the syntax of a operation follows the pattern `<operation name>(<field name> [, <arguments>])`, like
the expression `equals(active, true)` that is an operation with name `equals` on field `active` with the `true` as
an argument, which will lead to a filter to only return the data where the attribute `active` equals `true`.

When needed, a expression can be created to have multiple conditions and/or groups of conditions, just like the
expression `equals(active)&&(lowerThan(age, 18)||isNull(age))`.

Field names can contain alphanumeric characters. When needed to reference a multi-level attribute (such as when ToMany
or ToOne relationships are being used), the dot can be used to indicate the child, grandchild an alike fields, like
`product.group.name` (as in `equals(product.group.name, "Groceries")`).

Lastly, argument values can either be boolean literals (`true` or `false`), numeric values where the dot is the decimal
separator and String literals encapsulated by double quotes (like `"Lorem ipsum dolor sit amet"`).

### Supported operations

The table bellow contains the relation of operations the Dynamic Query supports.

| Operation           | Syntax                                          | Description                                                                                                                                                      | 
|---------------------|-------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| equals              | equals([field], [value])                        | Equality comparison where the field must have the exact provided value                                                                                           |
| notEquals           | notEquals([field], [value])                     | Inequality comparison where the field must have anything but the provided value                                                                                  |
| equalsIgnoreCase    | equalsIgnoreCase([field], [value])              | Equality comparison where the field must have the exact provided value without taking case into account                                                          |
| notEqualsIgnoreCase | notEqualsIgnoreCase([field], [value])           | Inequality comparison where the field must have anything but the provided value without taking case into account                                                 |
| lessThan            | lessThan([field], [value])                      | Comparison operator where the field must have a value that is less than the provided one                                                                         |
| lessOrEquals        | lessOrEquals([field], [value])                  | Comparison operator where the field must have a value that is less or equal than the provided one                                                                |
| greaterThan         | greaterThan([field], [value])                   | Comparison operator where the field must have a value that is greater than the provided one                                                                      |
| greaterOrEquals     | greaterOrEquals([field], [value])               | Comparison operator where the field must have a value that is greater or equal than the provided one                                                             |
| between             | between([field], [start], [end])                | Comparison operator where the field must have a value that is between the provided range start and end values                                                    |
| notBetween          | notBetween([field], [start], [end])             | Comparison operator where the field must have a value that isn't between the provided range start and end values                                                 |
| in                  | in([field], [value 1], [value 2], [value N])    | Equality operator where the field must have a value that is one of the provided values                                                                           |
| notIn               | notIn([field], [value 1], [value 2], [value N]) | Inequality operator where the field must have a value that is anything but one of the provided values                                                            |
| like                | like([field], [value])                          | Comparison operator where the field must have a value that likes (as in the SGBD's `LIKE` operation) the provided value                                          |
| notLike             | notLike([field], [value])                       | Comparison operator where the field must have a value that doesn't likes (as in the SGBD's `LIKE` operation) the provided value                                  |
| likeIgnoreCase      | likeIgnoreCase([field], [value])                | Comparison operator where the field must have a value that likes (as in the SGBD's `LIKE` operation) the provided value without taking case into account         |
| notLikeIgnoreCase   | notLikeIgnoreCase([field], [value])             | Comparison operator where the field must have a value that doesn't likes (as in the SGBD's `LIKE` operation) the provided value without taking case into account |
| isNull              | isNull([field])                                 | Comparison operator where the field must be null                                                                                                                 |
| isNotNull           | isNotNull([field])                              | Comparison operator where the field can't be null                                                                                                                |
| isEmpty             | isEmpty([field])                                | Comparison operator where the field must be an empty collection                                                                                                  |
| isNotEmpty          | isNotEmpty([field])                             | Comparison operator where the field can't be an empty collection                                                                                                 |
| not                 | not([expression])                               | Negates the output of the inner expression(s)                                                                                                                    |

## Value types and conversions
Dynamic Query is designed to work as a simple way to apply filters that could be coming from outside the application
itself, like from a query parameter in a HTTP request. This means that the expressions can have a limited set of
types of the values, mostly numbers, booleans and strings.

JPA, on the other hand, is strongly typed and most operations require the values to have a valid type. For example,
if the field is an UUID, the right-side of a equals operation must be a valid UUID for it to work. You can try to
supply a String, but it will probably fail even if a valid String representation of a UUID is being used.

Such scenarios where a type conversion is needed are handled automatically by Dynamic Query. Using JPA APIs, it will 
detect the target type of the involved field and will automatically convert the value to such target type.

Below are the list of types that are natively supported:
- BigDecimal
- BigInteger
- Boolean
- Byte
- CharArray
- Double
- Float
- Integer
- LocalDate
- LocalDateTime
- Long
- OffsetDateTime
- OffsetTime
- Short
- String
- UUID
- ZonedDateTime
- Enums (by comparing the option name)

Although the list includes most of the needed types in a common scenario, that doesn't means that will cover all of
your applications' scenarios. If needed, you can extend (or even replace a native conversion) this behaviour by
implementing the interface `ValueParser` and registering your class using `ValueParsers#register` (if you're using 
Spring, the `dynamic-query-spring-boot` module will register it automatically for you).

```java
public class ExampleParser implements ValueParser<Example> {

    /**
     * Priority of this parser. Lower numbers means higher priorities.
     */
    public int getPriority() {
        return 1;
    }

    /**
     * Checks if the parsing scenario is supported by the implementation
     * 
     * @param value Value to be parsed
     * @param targetType Target type of the conversion (the type of the attribute)
     */
    public boolean supports(String value, Class<?> targetType) { ... }

    /**
     * Executes the parsing. Only called if supports method returns true.
     *
     * @param value Value to be parsed
     * @param targetType Target type of the conversion (the type of the attribute)
     */
    public Example parse(String value, Class<?> targetType) { ... }
    
}
```

## Path rewriting
Dynamic Query uses the attribute names of the JPA entities to produce the query's predicates. At the same time,
the DSL was build for use as a public resource, not only internally in the application. 

If the application's public API contract (like the JSON structure of a REST API) isn't a one-to-one match with the
internal entities contract, odd scenarios can be produced where the user needs to learn two different names for the
same information, one in the public API contract and another in the Dynamic Query's DSL.

To handle such scenarios, Dynamic Query's can be extended with the `PathConverter` interface. Your application can
provide one or more implementations (by registering tem using `PathConverters#register`; if you're using Spring Boot, 
the `dynamic-query-spring-boot` module will register it automatically for you) that will be called to convert from the 
public name to the internal one, like changing from `product.factoryLocation` to `product.factory.location` in the
`equals(product.factoryLocation, "Brazil")` expression.

```java
public class ExampleConverter implements PathConverter {

    /**
     * Priority of this converter. Lower numbers means higher priorities.
     */
    public int getPriority() {
        return 1;
    }

    /**
     * Checks if the conversion scenario is supported by the implementation
     *
     * @param path Path of the attribute that needs to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    public boolean supports(String path, Path<?> startPoint) { ... }

    /**
     * Executes the conversion. Only called if supports method returns true.
     *
     * @param path Path of the attribute that needs to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    public String convert(String path, Path<?> startPoint) { ... }

}
```
