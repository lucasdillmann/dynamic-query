package br.com.dillmann.dynamicquery.grammar

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

/**
 * ANTLR listener for parsing errors
 */
object DynamicQueryGrammarErrorListener: BaseErrorListener() {

    /**
     * Notifies about a syntax error
     *
     * @param recognizer Implementation that tried to recognize the token
     * @param offendingSymbol Symbol on where the error was found
     * @param line Line number on where the error happened
     * @param charPositionInLine Character position on where the error start
     * @param message Description about the problem
     * @param exception Thrown exception during the parse
     */
    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        message: String,
        exception: RecognitionException?
    ): Nothing {
        throw DynamicQueryGrammarException(line, charPositionInLine, message, offendingSymbol, exception)
    }
}
