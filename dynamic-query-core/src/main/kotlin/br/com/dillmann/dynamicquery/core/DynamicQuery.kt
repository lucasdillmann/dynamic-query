package br.com.dillmann.dynamicquery.core

import br.com.dillmann.dynamicquery.core.grammar.DynamicQueryDslParserFactory
import br.com.dillmann.dynamicquery.core.grammar.converter.GrammarConverter
import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification

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
     * @throws DynamicQueryException when an error happens while parsing the given expression
     */
    @JvmStatic
    fun parse(expression: String): DynamicQuerySpecification {
        val converter = GrammarConverter()
        val parser = DynamicQueryDslParserFactory.build(expression)

        parser.addParseListener(converter)
        parser.root() // execute the parse from the root element

        return converter.specification()
    }
}
