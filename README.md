# Dynamic Query for JPA

This project provides a simple and easy to use DSL for dynamic queries backed by a JPA-compliant ORM, like Hibernate.
Main goal is to enable a simple way for applications, mainly REST APIs, to have the possibility to apply dynamic
filters in the data that the application manages without the need to hardcode every single business scenario or search
possibility.

To exemplify, let's assume that you have a REST API for a basic list of products. With Dynamic Query, you could make a 
request like the following one to return from such API only the products from a given manufacturer that are active and 
have a price lower than 10, all without the need to code such search possibility.

```http request
GET /api/products?query=equals(manufacturer.id, 25)%26%26equals(active, true)%26%26lessThan(unitPrice, 10)
```

## Getting started

To get started, you'll need to include the Dynamic Query in your application (via Maven, Gradle, sbt, etc.).
Dynamic Query isn't available through Maven Central yet, but the publication is in the works.

```xml
<dependencies>
    <dependency>
        <group>br.com.dillmann.dynamicquery</group>
        <artifactId>dynamic-query</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

If you're using Spring Boot, adding the `dynamic-query-spring-boot-data-jpa` and `dynamic-query-spring-boot-web` 
modules instead will enable you to inject an instance of the `DynamicQuerySpecification` directly on your controller 
and forward it to a repository.

The value provided as the `DynamicQuerySpecification` instance will be retrieved by automatically parsing the value 
from the "query" request parameter. When such parameter doesn't exist in the request, a null value will be provided
to the controller.

```xml
<dependencies>
    <dependency>
      <group>br.com.dillmann.dynamicquery</group>
      <artifactId>dynamic-query-spring-boot-data-jpa</artifactId>
      <version>1.0.0</version>
    </dependency>
    <dependency>
      <group>br.com.dillmann.dynamicquery</group>
      <artifactId>dynamic-query-spring-boot-web</artifactId>
      <version>1.0.0</version>
    </dependency>
</dependencies>
```

With those dependencies, you can do something like this in your code in order to use the Dynamic Query's functionality:

```java
import br.com.dillmann.dynamicquery.springboot.datajpa.DynamicQueryRepository;
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification;

@Repository
public interface ExampleRepository 
    extends JpaRepository<Example, Long>, DynamicQueryRepository<Example> {
}

@RestController
public class ExampleController {
    
    private final ExampleRepository repository;
    
    @Autowired
    public ExampleController(final ExampleRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping
    public ResponseEntity<Page<Example>> example(
        final DynamicQuerySpecification dynamicQuery, 
        final Pageable page
    ) {
        // DynamicQuerySpecification will be parsed automatically using the "query" request 
        // parameter value. When such parameter doesn't exist in the request, the 
        // DynamicQuerySpecification will be null.
        // This is an example only. Using the repository right on the controller isn't a
        // good pattern to be used in production code.
        final Page<Example> page = repository.findAll(dynamicQuery, page);
        return ResponseEntity.ok(page);
    }
}
```

If you're using anything else, with just the previously mentioned `dynamic-query` module in the classpath you can use 
the `DynamicQuery` class to parse any expression formatted as a `String` into a JPA-compliant specification, which can
be used to filter the query results. The following code exemplifies the process of parsing and using the specification 
in a JPA query.

```java
// Initialize the query using JPA Criteria APIs
final EntityManager entityManager = Persistence
    .createEntityManagerFactory("example")
    .createEntityManager();
final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
final CriteriaQuery<EntityClass> criteriaQuery = builder.createQuery(EntityClass.class);
final Root<EntityClass> root = criteriaQuery.getRoots().iterator().next();

// Parses a dynamic expression using Dynamic Query to a JPA Predicate
// This portion is the only code that goes beyond your (probably) regular JPA query code
final String expression = "equals(active, true)||isNull(active)";
final DynamicQuerySpecification specification = DynamicQuery.parse(expression);
final Predicate predicate = specification.toPredicate(root, criteriaQuery, builder);

// Apply the predicate and retrieve the query results
final TypedQuery<EntityClass> query = entityManager
    .createQuery(criteriaQuery.where(predicate));
final List<EntityClass> queryResults = query.getResultList();
```

### Examples

If you are a developer that learns quickly by example, check-out the [`examples`](examples) folder. There's some
example projects there with the Dynamic Query integrated for you. Just note that such projects are for demo purposes
only, they aren't intended to be used as a template or something like that.

### Requirements
The Dynamic Query library requires the JVM 17 or later to be used. When the project is based on the Spring Boot, version 
3.0.0 or later of the framework is required.

## Building a Dynamic Query expression

A Dynamic Query expression is, at its core, a set of instructions that will be applied to a query as the conditions
that the data must meet. An expression is a compound of one or more operations and logic operators (AND/OR) between
them.

In general, the syntax of an operation follows the pattern `<operation name>([<argument>, <argument>])`, like
the expression `equals(active, true)` that is an operation with name `equals` with field name `active` and `true` as
the arguments, which will lead to a filter to only return the data where the attribute `active` equals to `true`.

When needed, an expression can be created to have multiple conditions and/or groups of conditions, just like the
expression `equals(active)&&(lowerThan(age, 18)||isNull(age))`. They can also have inlined transformation 
operations, like a uppercase/lowercase such as `equals(lower(name), "john")`

Field names can contain alphanumeric characters. When needed to reference a multi-level attribute (such as when ToMany
or ToOne relationships are being used), the dot can be used to indicate the child, grandchild an alike fields, like
`product.group.name` (as in `equals(product.group.name, "Groceries")`).

Direct comparisons between fields are also supported, like in the expression
`notBetween(unitPrice, product.minimumPrice, product.maximumPrice)` which could return all SKUs of a sale that the unit
price is not in the expected/allowed range.

Lastly, argument values can either be boolean literals (`true` or `false`), numeric values where the dot is the decimal
separator and String literals encapsulated by double quotes (like `"Lorem ipsum dolor sit amet"`).

### Supported operations

#### Predicates

The table bellow contains the relation of predicate operations the Dynamic Query supports.

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

#### Transformations and calculations

The table bellow contains the relation of transformation operations the Dynamic Query supports.

| Operation        | Syntax                                  | Description                                                                                                                       | 
|------------------|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| abs              | abs([number])                           | Returns the absolute value of the argument value                                                                                  |
| avg              | avg([number])                           | Returns the average value of the argument value                                                                                   |
| ceiling          | ceiling([number])                       | Returns the ceiling value of the argument value (smallest integer greater than or equal to the argument)                          |
| coalesce         | coalesce([value], [value])              | Expression that returns a null value if all arguments are null or the value of the first non-null argument                        |
| concat           | concat([string], [string])              | Concatenates the two given Strings into a single one                                                                              |
| count            | count([value])                          | Aggregation expression that returns the total count                                                                               |
| countDistinct    | countDistinct([value])                  | Aggregation expression that returns the distinct count                                                                            |
| currentDate      | currentDate()                           | Returns the current date                                                                                                          |
| currentTime      | currentTime()                           | Returns the current time                                                                                                          |
| currentTimestamp | currentTimestamp()                      | Returns the current date and time                                                                                                 |
| diff             | diff([number], [number])                | Returns the difference between two numbers                                                                                        |
| exp              | exp([number])                           | Returns the exponential value (Euler's number)                                                                                    |
| floor            | floor([value])                          | Returns the floor value of the argument value (largest integer smaller than or equal to the argument)                             |
| length           | length([string])                        | Returns the length (amount of chars) of a String                                                                                  |
| localDate        | localDate()                             | Returns the current local (at the server's timezone) date                                                                         |
| localTime        | localTime()                             | Returns the current local (at the server's timezone) time                                                                         |
| localDateTime    | localDateTime()                         | Returns the current local (at the server's timezone) date and time                                                                |
| locate           | locate([string], [string])              | Returns the position (1 indexed) of the second string argument within the first string. When not found, zero is returned instead. |
| lower            | lower([string])                         | Returns the argument string with all characters in lowercase                                                                      |
| max              | max([number])                           | Aggregation expression that returns the biggest number                                                                            |
| min              | min([number])                           | Aggregation expression that returns the lowest number                                                                             |
| mod              | mod([number], [number])                 | Returns the modulus (remainder under a integer division) of the arguments                                                         |
| neg              | neg([number])                           | Returns the arithmetic negation of the argument                                                                                   |
| power            | power([number], [number])               | Returns the first argument raised to the power of the second                                                                      |
| prod             | prod([number], [number])                | Returns the product of the arguments                                                                                              |
| quot             | quot([number], [number])                | Returns the quotient of the arguments                                                                                             |
| size             | size([collection])                      | Returns the size of the argument collection                                                                                       |
| sqrt             | sqrt([number])                          | Returns the square root of the argument number                                                                                    |
| substring        | substring([string], [number])           | Returns the portion of the string after the given position (second argument)                                                      |
| substring        | substring([string], [number], [number]) | Returns the portion of the string between the given range (second and third arguments)                                            |
| sum              | sum([number])                           | Returns the sum of all values of the argument number collection                                                                   |
| sum              | sum([number], [number])                 | Returns the sum of both argument numbers                                                                                          |
| toBigDecimal     | toBigDecimal([number])                  | Converts and returns the given number as a BigDecimal                                                                             |
| toBigInteger     | toBigInteger([number])                  | Converts and returns the given number as a BigInteger                                                                             |
| toDouble         | toDouble([number])                      | Converts and returns the given number as a Double                                                                                 |
| toFloat          | toFloat([number])                       | Converts and returns the given number as a Float                                                                                  |
| toInteger        | toInteger([number])                     | Converts and returns the given number as a Integer                                                                                |
| toLong           | toLong([number])                        | Converts and returns the given number as a Long                                                                                   |
| trim             | trim([string])                          | Removes all blanks (spaces) from both ends of the string                                                                          |
| upper            | upper([string])                         | Returns the argument string with all characters in uppercase                                                                      |

## Value types and conversions
Dynamic Query is designed to work as a simple way to apply filters that will be coming from outside the application
itself, like from a query parameter in an HTTP request. This means that the expressions can have a limited set of
types of the values, mostly numbers, booleans and strings.

JPA, on the other hand, is strongly typed and most operations require the values to have a valid type. For example,
if the field is a UUID, the right-side of an equals operation must be a valid UUID for it to work. You can try to
supply a String, but it will probably fail even if a valid String representation of a UUID is being used.

Such scenarios where a type conversion is needed are handled automatically by Dynamic Query. Using JPA APIs, it will 
detect the target type of the involved field and will try to automatically convert the value to such target type.

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

Although the list includes most of the needed types in a common scenario, that doesn't mean that will cover all of
your applications' scenarios. If needed, you can extend (or even replace a native/default conversion) this behaviour by
implementing the interface `ValueParser` and registering your class using `ValueParsers#register` (if you're using 
Spring, the `dynamic-query-spring-boot` module will register it automatically for you).

```java
public class ExampleValueParser implements ValueParser<Example> {

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
    public boolean supports(String value, Class<?> targetType) {
    }

    /**
     * Executes the parsing. Only called if supports method returns true.
     *
     * @param value Value to be parsed
     * @param targetType Target type of the conversion (the type of the attribute)
     */
    public Example parse(String value, Class<?> targetType) {
    }
    
}
```

## Path rewriting
Dynamic Query uses the attribute names of the JPA entities to produce the query's predicates. At the same time,
the DSL was build for use as a public resource, not only internally in the application. 

If the application's public API contract (like the JSON structure of a REST API) isn't a one-to-one match with the
internal entities contract, odd scenarios can be produced where the user needs to learn two different names for the
same information, one in the public API contract and another in the Dynamic Query's DSL.

To handle such scenarios, Dynamic Query's can be extended with the `PathConverter` interface. Your application can
provide one or more implementations (by registering tem using `PathConverters#register` or, if you're using Spring Boot, 
the `dynamic-query-spring-boot` module will register it automatically for you) that will be called to convert from the 
public name to the internal one, like changing from `product.factoryLocation` to `product.factory.location` in the
`equals(product.factoryLocation, "Brazil")` expression.

```java
public class ExamplePathConverter implements PathConverter {

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
    public boolean supports(String path, Path<?> startPoint) {
    }

    /**
     * Executes the conversion. Only called if supports method returns true.
     *
     * @param path Path of the attribute that needs to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    public String convert(String path, Path<?> startPoint) {
    }

}
```
