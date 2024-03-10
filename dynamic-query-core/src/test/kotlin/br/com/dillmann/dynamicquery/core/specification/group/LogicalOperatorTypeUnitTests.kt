package br.com.dillmann.dynamicquery.core.specification.group

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * [LogicalOperatorType] unit tests
 */
class LogicalOperatorTypeUnitTests {

    @Test
    fun `AND option should have the expected identifier`() {
        assertEquals("&&", LogicalOperatorType.AND.identifier)
    }

    @Test
    fun `OR option should have the expected identifier`() {
        assertEquals("||", LogicalOperatorType.OR.identifier)
    }
}
