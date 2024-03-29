package br.com.dillmann.dynamicquery.grammar

import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarParser
import br.com.dillmann.dynamicquery.grammar.dsl.DynamicQueryGrammarLexer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * [DynamicQueryGrammarParser] instance factory
 */
object DynamicQueryGrammarParserFactory {

    /**
     * Builds and returns a [DynamicQueryGrammarParser] for the given input expression
     *
     * @param input Expression to be parsed by the produced parser
     */
    fun build(input: String): DynamicQueryGrammarParser {
        val inputStream = CharStreams.fromString(input)
        val lexer = DynamicQueryGrammarLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = DynamicQueryGrammarParser(tokenStream)

        parser.removeErrorListeners()
        parser.removeParseListeners()
        parser.addErrorListener(DynamicQueryGrammarErrorListener)

        return parser
    }
}
