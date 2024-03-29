package br.com.dillmann.dynamicquery

import br.com.dillmann.dynamicquery.grammar.DynamicQueryGrammarParserFactory
import br.com.dillmann.dynamicquery.grammar.DynamicQueryGrammarException
import br.com.dillmann.dynamicquery.grammar.converter.GrammarConverter
import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification

/**
 * Facade class for the Dynamic Query library
 */
object DynamicQuery {

    /**
     * Parses the given expression as a Dynamic Query DSL formatted value, returning the corresponding parse result
     * as a JPA-based specification
     *
     * @param expression Expression to be parsed
     * @return Parsed result as a [DynamicQuerySpecification]
     * @throws DynamicQueryGrammarException when an error happens while parsing the given expression
     */
    @JvmStatic
    fun parse(expression: String): DynamicQuerySpecification {
        val converter = GrammarConverter()
        val parser = DynamicQueryGrammarParserFactory.build(expression)

        parser.addParseListener(converter)
        parser.root() // execute the parse from the root element

        val rootNode = converter.rootNode()
        return DynamicQueryCompiler.compile(rootNode)
    }
}
