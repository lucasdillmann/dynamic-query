package br.com.dillmann.dynamicquery.grammar.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * [TreeNodeType] unit tests
 */
class TreeNodeTypeUnitTests {

    @Test
    fun `GROUP option should have allowsChildren set to true`() {
        assertTrue {
            TreeNodeType.GROUP.allowsChildren
        }
    }

    @Test
    fun `PREDICATE_OPERATION option should have allowsChildren set to true`() {
        assertTrue {
            TreeNodeType.PREDICATE_OPERATION.allowsChildren
        }
    }

    @Test
    fun `TRANSFORMATION_OPERATION option should have allowsChildren set to false`() {
        assertTrue {
            TreeNodeType.TRANSFORMATION_OPERATION.allowsChildren
        }
    }

    @Test
    fun `PARAMETER_LITERAL option should have allowsChildren set to false`() {
        assertFalse {
            TreeNodeType.PARAMETER_LITERAL.allowsChildren
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
