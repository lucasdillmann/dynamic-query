package br.com.dillmann.dynamicquery.core

import org.junit.jupiter.api.Test

/**
 * [DynamicQuery] integration tests
 */
class DynamicQueryIntegrationTests {

    @Test
    fun `parse should be able to parse composite expressions`() {
        // scenario
        val inputs = listOf(
            "greaterOrEquals(age, 18)||equals(emancipated, true)",
            "equals(active, true)&&(equals(address.city, \"Lorem ipsum\")||equals(address.state, \"Dolor sit amet\"))",
            "(isNull(active)||notEquals(active, true))&&between(age, 25, 30)&&notBetween(monthOfBirth, 1, 3)",
            "(((notEquals(loremIpsum, FALSE))))",
            "not(equals(active, true)||isNull(active))",
        )

        // execution
        inputs.forEach(DynamicQuery::parse)
    }

    @Test
    fun `parse should be able to parse simple expressions`() {
        // scenario
        val inputs = listOf(
            "equals(field.name, 25)",
            "notEquals(field.name, 25)",
            "equalsIgnoreCase(test, \"Lorem ipsum\")",
            "notEqualsIgnoreCase(test, \"Dolor sit amet\")",
            "lessThan(test, 25)",
            "lessOrEquals(test, 55)",
            "greaterThan(test, 23)",
            "greaterOrEquals(test, 3)",
            "between(lorem.ipsum, \"2022-01-04\", \"2022-01-04\")",
            "notBetween(lorem.ipsum, \"2022-01-04\", \"2022-01-04\")",
            "in(amount, 1, 2, 3, 4, 5, 6)",
            "notIn(amount, \"teste\", \"lorem ipsum\")",
            "like(someField, \"Maria\")",
            "notLike(someField, \"João\")",
            "likeIgnoreCase(someField, \"MARIA\")",
            "notLikeIgnoreCase(someField, \"JOÃO\")",
            "isNull(test)",
            "isNotNull(loremIpsum)",
            "isEmpty(collectionFieldName)",
            "isNotEmpty(collectionFieldName)",
        )

        // execution
        inputs.forEach(DynamicQuery::parse)
    }
}
