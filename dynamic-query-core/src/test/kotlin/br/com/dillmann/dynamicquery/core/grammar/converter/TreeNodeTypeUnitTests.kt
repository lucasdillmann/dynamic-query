package br.com.dillmann.dynamicquery.core.grammar.converter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * [TreeNodeType] unit tests
 */
class TreeNodeTypeUnitTests {

    @Test
    fun `ROOT option should have allowsChildren set to true`() {
        assertTrue {
            TreeNodeType.ROOT.allowsChildren
        }
    }

    @Test
    fun `GROUP option should have allowsChildren set to true`() {
        assertTrue {
            TreeNodeType.GROUP.allowsChildren
        }
    }

    @Test
    fun `FILTER option should have allowsChildren set to false`() {
        assertFalse {
            TreeNodeType.FILTER.allowsChildren
        }
    }

    @Test
    fun `LOGICAL_OPERATOR option should have allowsChildren set to false`() {
        assertFalse {
            TreeNodeType.LOGICAL_OPERATOR.allowsChildren
        }
    }

    @Test
    fun `NEGATION option should have allowsChildren set to true`() {
        assertTrue {
            TreeNodeType.NEGATION.allowsChildren
        }
    }
}
