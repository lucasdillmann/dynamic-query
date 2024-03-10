package br.com.dillmann.dynamicquery.core.grammar

import br.com.dillmann.dynamicquery.core.grammar.exception.SyntaxException
import br.com.dillmann.dynamicquery.core.randomInt
import br.com.dillmann.dynamicquery.core.randomString
import io.mockk.mockk
import org.antlr.v4.runtime.RecognitionException
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * [GrammarErrorListener] unit tests
 */
class GrammarErrorListenerUnitTests {

    @Test
    fun `syntaxError should throw a SyntaxException with the expected values`() {
        // scenario
        val offendingSymbol = mockk<Any>()
        val line = randomInt
        val charPosition = randomInt
        val errorDetails = randomString
        val recognitionException = mockk<RecognitionException>()

        // execution
        val result = runCatching {
            GrammarErrorListener.syntaxError(
                null,
                offendingSymbol,
                line,
                charPosition,
                errorDetails,
                recognitionException
            )
        }

        // validation
        assertTrue { result.isFailure }
        assertTrue { result.exceptionOrNull() is SyntaxException }

        val exception = result.exceptionOrNull() as SyntaxException
        assertEquals(line, exception.line)
        assertEquals(charPosition, exception.position)
        assertEquals(offendingSymbol, exception.symbol)
        assertEquals(recognitionException, exception.cause)
        assertEquals(
            "Unable to parse the requested query due to an error on position $charPosition: $errorDetails",
            exception.message
        )
    }
}
