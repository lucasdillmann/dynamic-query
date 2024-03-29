package br.com.dillmann.dynamicquery.grammar

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarLexer
import org.antlr.v4.runtime.misc.Interval
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * [DynamicQueryGrammarParserFactory] unit tests
 */
class DynamicQueryGrammarParserFactoryUnitTests {

    @Test
    fun `build should produce a parser with the provided value as the expression to be parsed`() {
        // scenario
        val expression = randomString

        // execution
        val result = DynamicQueryGrammarParserFactory.build(expression)

        // validation
        val inputStream = result.tokenStream.tokenSource.inputStream
        val interval = Interval.of(0, inputStream.size())
        val resultExpression = inputStream.getText(interval)
        assertEquals(expression, resultExpression)
    }

    @Test
    fun `build should produce a parser with the expected lexer`() {
        // execution
        val result = DynamicQueryGrammarParserFactory.build(randomString)

        // validation
        assertTrue { result.tokenStream.tokenSource is DynamicQueryGrammarLexer }
    }

    @Test
    fun `build should produce a parser with only the GrammarErrorListener attached`() {
        // execution
        val result = DynamicQueryGrammarParserFactory.build(randomString)

        // validation
        assertTrue { result.parseListeners.isEmpty() }
        assertTrue { DynamicQueryGrammarErrorListener in result.errorListeners }
        assertEquals(1, result.errorListeners.size)
    }
}
