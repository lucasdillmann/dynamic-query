package br.com.dillmann.dynamicquery.core.grammar.exception

/**
 * [GrammarException] for the scenarios where the DSL couldn't be parsed due to a syntax error
 *
 * @param line Line number on where the error happened
 * @param position Character position on where the error starts
 * @param errorDescription Brief description of what happened
 * @param symbol Offending symbol
 * @param cause Cause of the parse error
 */
class SyntaxException(
    val line: Int,
    val position: Int,
    val errorDescription: String,
    val symbol: Any?,
    cause: Exception?,
): GrammarException(
    "Unable to parse the requested query due to an error on position $position: $errorDescription",
    cause,
)
