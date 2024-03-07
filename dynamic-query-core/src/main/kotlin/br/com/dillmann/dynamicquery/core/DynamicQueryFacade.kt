package br.com.dillmann.dynamicquery.core

import br.com.dillmann.dynamicquery.core.grammar.GrammarParserFactory
import br.com.dillmann.dynamicquery.core.grammar.converter.GrammarConverter
import br.com.dillmann.dynamicquery.core.specification.Specification

/**
 * Facade class for the Dynamic Query library
 */
object DynamicQueryFacade {

    /**
     * Parses the given expression as a Dynamic Query DSL formatted value, returning the corresponding parse result
     * as a JPA-based specification
     *
     * @param expression Expression to be parsed
     * @return Parsed result as a [Specification]
     * @throws DynamicQueryException when an error happens while parsing the given expression
     */
    @JvmStatic
    fun parse(expression: String): DynamicQuery {
        val converter = GrammarConverter()
        val parser = GrammarParserFactory.build(expression)

        parser.addParseListener(converter)
        parser.root() // execute the parse from the root element

        val specification = converter.specification()
        return DynamicQuery(expression, specification)
    }
}
