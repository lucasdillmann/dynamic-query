package br.com.dillmann.dynamicquery.springbootweb

/**
 * Public API contract for the Dynamic Query's HTTP error responses
 *
 * @property message Human-readable message with a summary about what happened
 * @property details Optional details of the error
 */
class DynamicQueryWebErrorDto(
    val message: String,
    val details: Map<String, Any>?,
)
