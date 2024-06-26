package br.com.dillmann.dynamicquery.springboot.web

import br.com.dillmann.dynamicquery.grammar.DynamicQueryGrammarException
import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.specification.exception.UnknownAttributeNameException
import br.com.dillmann.dynamicquery.specification.exception.UnsupportedValueTypeException
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

/**
 * [DynamicQueryWebErrorHandler] unit tests
 */
class DynamicQueryWebErrorHandlerUnitTests {

    private val handler = DynamicQueryWebErrorHandler()

    @Test
    fun `handle for an InvalidArgumentCountException should produce the expected response`() {
        // scenario
        val exception = mockk<InvalidArgumentCountException>(relaxed = true)
        val expectedPayload = DynamicQueryWebErrorDto(
            exception.message!!,
            mapOf(
                "minimumArgumentCount" to exception.minimumArgumentCount,
                "maximumArgumentCount" to exception.maximumArgumentCount,
                "currentArgumentCount" to exception.currentArgumentCount,
                "predicateType" to exception.operation,
            ),
        )
        val expectedResponse = ResponseEntity.badRequest().body(expectedPayload)

        // execution
        val result = handler.handle(exception)

        // validation
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `handle for an UnknownAttributeNameException should produce the expected response`() {
        // scenario
        val exception = mockk<UnknownAttributeNameException>(relaxed = true)
        val expectedPayload = DynamicQueryWebErrorDto(
            exception.message!!,
            mapOf(
                "path" to exception.path,
            ),
        )
        val expectedResponse = ResponseEntity.badRequest().body(expectedPayload)

        // execution
        val result = handler.handle(exception)

        // validation
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `handle for an UnsupportedValueTypeException should produce the expected response`() {
        // scenario
        val exception = mockk<UnsupportedValueTypeException>(relaxed = true)
        val expectedPayload = DynamicQueryWebErrorDto(
            exception.message!!,
            mapOf(
                "value" to exception.value,
                "operationName" to exception.operationName,
            ),
        )
        val expectedResponse = ResponseEntity.badRequest().body(expectedPayload)

        // execution
        val result = handler.handle(exception)

        // validation
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `handle for a SyntaxException should produce the expected response`() {
        // scenario
        val exception = mockk<DynamicQueryGrammarException>(relaxed = true)
        val expectedPayload = DynamicQueryWebErrorDto(
            exception.message!!,
            mapOf(
                "line" to exception.line,
                "position" to exception.position,
                "errorDescription" to exception.errorDescription,
            ),
        )
        val expectedResponse = ResponseEntity.badRequest().body(expectedPayload)

        // execution
        val result = handler.handle(exception)

        // validation
        assertEquals(expectedResponse, result)
    }

}
