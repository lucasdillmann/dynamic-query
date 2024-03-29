package br.com.dillmann.dynamicquery.springboot.web

import br.com.dillmann.dynamicquery.grammar.DynamicQueryGrammarException
import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.specification.exception.UnknownAttributeNameException
import br.com.dillmann.dynamicquery.specification.exception.UnsupportedValueTypeException
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Dynamic Query's error handler for the Spring Web's controllers
 */
@ControllerAdvice
@ConditionalOnProperty(
    name = ["dynamic-query.web.enable-error-handler"],
    havingValue = "true",
    matchIfMissing = true,
)
class DynamicQueryWebErrorHandler {

    /**
     * Exception handler for the [InvalidArgumentCountException]
     *
     * @param exception Thrown exception
     */
    @ExceptionHandler
    fun handle(exception: InvalidArgumentCountException) =
        buildResponse(
            HttpStatus.BAD_REQUEST,
            exception.message,
            mapOf(
                "attributeName" to exception.attributeName,
                "minimumArgumentCount" to exception.minimumArgumentCount,
                "maximumArgumentCount" to exception.maximumArgumentCount,
                "currentArgumentCount" to exception.currentArgumentCount,
                "predicateType" to exception.predicateType,
            ),
        )

    /**
     * Exception handler for the [UnknownAttributeNameException]
     *
     * @param exception Thrown exception
     */
    @ExceptionHandler
    fun handle(exception: UnknownAttributeNameException) =
        buildResponse(
            HttpStatus.BAD_REQUEST,
            exception.message,
            mapOf(
                "path" to exception.path,
            ),
        )

    /**
     * Exception handler for the [UnsupportedValueTypeException]
     *
     * @param exception Thrown exception
     */
    @ExceptionHandler
    fun handle(exception: UnsupportedValueTypeException) =
        buildResponse(
            HttpStatus.BAD_REQUEST,
            exception.message,
            mapOf(
                "value" to exception.value,
                "operationName" to exception.operationName,
            ),
        )

    /**
     * Exception handler for the [DynamicQueryGrammarException]
     *
     * @param exception Thrown exception
     */
    @ExceptionHandler
    fun handle(exception: DynamicQueryGrammarException) =
        buildResponse(
            HttpStatus.BAD_REQUEST,
            exception.message,
            mapOf(
                "line" to exception.line,
                "position" to exception.position,
                "errorDescription" to exception.errorDescription,
            ),
        )

    private fun buildResponse(
        status: HttpStatus,
        message: String? = null,
        errorDetails: Map<String, Any>? = null
    ): ResponseEntity<DynamicQueryWebErrorDto> {
        val targetMessage = message ?: status.reasonPhrase
        val payload = DynamicQueryWebErrorDto(targetMessage, errorDetails)
        return ResponseEntity.status(status).body(payload)
    }
}
