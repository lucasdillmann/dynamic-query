package br.com.dillmann.dynamicquery.core.grammar

import br.com.dillmann.dynamicquery.core.grammar.dsl.DynamicQueryDslLexer
import br.com.dillmann.dynamicquery.core.grammar.dsl.DynamicQueryDslParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * [DynamicQueryDslParser] instance factory
 */
object DynamicQueryDslParserFactory {

    /**
     * Builds and returns a [DynamicQueryDslParser] for the given input expression
     *
     * @param input Expression to be parsed by the produced parser
     */
    fun build(input: String): DynamicQueryDslParser {
        val inputStream = CharStreams.fromString(input)
        val lexer = DynamicQueryDslLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = DynamicQueryDslParser(tokenStream)

        parser.removeErrorListeners()
        parser.removeParseListeners()
        parser.addErrorListener(GrammarErrorListener)

        return parser
    }
}
