package br.com.dillmann.dynamicquery.springbootweb

import br.com.dillmann.dynamicquery.core.DynamicQueryException
import br.com.dillmann.dynamicquery.core.grammar.exception.SyntaxException
import br.com.dillmann.dynamicquery.core.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.core.specification.exception.UnknownAttributeNameException
import br.com.dillmann.dynamicquery.core.specification.exception.UnsupportedValueTypeException
import io.mockk.every
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
    fun `handle for a DynamicQueryException should produce the expected response`() {
        // scenario
        val expectedPayload = DynamicQueryWebErrorDto("Internal Server Error", null)
        val expectedResponse = ResponseEntity.internalServerError().body(expectedPayload)
        val exception = mockk<DynamicQueryException> {
            every { message } returns randomString
            every { cause } returns null
            every { suppressed } returns null
            every { stackTrace } returns emptyArray()
        }

        // execution
        val result = handler.handle(exception)

        // validation
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `handle for an InvalidArgumentCountException should produce the expected response`() {
        // scenario
        val exception = mockk<InvalidArgumentCountException>(relaxed = true)
        val expectedPayload = DynamicQueryWebErrorDto(
            exception.message!!,
            mapOf(
                "attributeName" to exception.attributeName,
                "minimumArgumentCount" to exception.minimumArgumentCount,
                "maximumArgumentCount" to exception.maximumArgumentCount,
                "currentArgumentCount" to exception.currentArgumentCount,
                "predicateType" to exception.predicateType,
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
        val exception = mockk<SyntaxException>(relaxed = true)
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
