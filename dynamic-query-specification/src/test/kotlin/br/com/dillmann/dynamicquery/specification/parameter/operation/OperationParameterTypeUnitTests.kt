package br.com.dillmann.dynamicquery.specification.parameter.operation

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * [OperationParameterType] unit tests
 */
class OperationParameterTypeUnitTests {

    @Test
    fun `ABS option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.ABS

        assertEquals("abs", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `AVG option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.AVG

        assertEquals("avg", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `CEILING option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.CEILING

        assertEquals("ceiling", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `COALESCE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.COALESCE

        assertEquals("coalesce", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `CONCAT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.CONCAT

        assertEquals("concat", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `COUNT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.COUNT

        assertEquals("count", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `COUNT_DISTINCT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.COUNT_DISTINCT

        assertEquals("countDistinct", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `CURRENT_DATE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.CURRENT_DATE

        assertEquals("currentDate", option.identifier)
        assertEquals(0..0, option.argumentCountRange)
    }

    @Test
    fun `CURRENT_TIME option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.CURRENT_TIME

        assertEquals("currentTime", option.identifier)
        assertEquals(0..0, option.argumentCountRange)
    }

    @Test
    fun `DIFF option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.DIFF

        assertEquals("diff", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `EXP option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.EXP

        assertEquals("exp", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `FLOOR option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.FLOOR

        assertEquals("floor", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `LENGTH option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LENGTH

        assertEquals("length", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `LOCAL_DATE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LOCAL_DATE

        assertEquals("localDate", option.identifier)
        assertEquals(0..0, option.argumentCountRange)
    }

    @Test
    fun `LOCAL_DATE_TIME option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LOCAL_DATE_TIME

        assertEquals("localDateTime", option.identifier)
        assertEquals(0..0, option.argumentCountRange)
    }

    @Test
    fun `LOCAL_TIME option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LOCAL_TIME

        assertEquals("localTime", option.identifier)
        assertEquals(0..0, option.argumentCountRange)
    }

    @Test
    fun `LOCATE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LOCATE

        assertEquals("locate", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `LOWER option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.LOWER

        assertEquals("lower", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `MAX option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.MAX

        assertEquals("max", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `MIN option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.MIN

        assertEquals("min", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `MOD option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.MOD

        assertEquals("mod", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `NEG option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.NEG

        assertEquals("neg", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `POWER option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.POWER

        assertEquals("power", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `PROD option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.PROD

        assertEquals("prod", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `QUOT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.QUOT

        assertEquals("quot", option.identifier)
        assertEquals(2..2, option.argumentCountRange)
    }

    @Test
    fun `SIZE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.SIZE

        assertEquals("size", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `SQRT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.SQRT

        assertEquals("sqrt", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `SUBSTRING option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.SUBSTRING

        assertEquals("substring", option.identifier)
        assertEquals(2..3, option.argumentCountRange)
    }

    @Test
    fun `SUM option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.SUM

        assertEquals("sum", option.identifier)
        assertEquals(1..2, option.argumentCountRange)
    }

    @Test
    fun `TO_BIG_DECIMAL option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_BIG_DECIMAL

        assertEquals("toBigDecimal", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TO_BIG_INTEGER option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_BIG_INTEGER

        assertEquals("toBigInteger", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TO_DOUBLE option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_DOUBLE

        assertEquals("toDouble", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TO_FLOAT option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_FLOAT

        assertEquals("toFloat", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TO_INTEGER option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_INTEGER

        assertEquals("toInteger", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TO_LONG option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TO_LONG

        assertEquals("toLong", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `TRIM option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.TRIM

        assertEquals("trim", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }

    @Test
    fun `UPPER option should have the expected identifier and parameter count range`() {
        val option = OperationParameterType.UPPER

        assertEquals("upper", option.identifier)
        assertEquals(1..1, option.argumentCountRange)
    }
}
