package br.com.dillmann.dynamicquery.core.pathconverter

import jakarta.persistence.criteria.Path

/**
 * Contract definition for a Path Rewriter able to, when needed, convert a path in the external form to the
 * internal one.
 *
 * The main goal of a path converter implementation is to convert between public and internal application's
 * contracts, like when the application is a REST API and the public attribute name doesn't directly map to the
 * internal, JPA entity one.
 */
interface PathConverter {

    /**
     * Priority of the converter. Lower numbers means higher priorities.
     */
    val priority: Int

    /**
     * Checks if the implementation supports the given path and/or starting point (normally the query root)
     *
     * @param path Path of the attribute that needs to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    fun supports(path: String, startPoint: Path<out Any>): Boolean

    /**
     * Converts the field path from the public form to the internal one. This method is only called when [supports]
     * returns true and should always return a non-null, non-empty value.
     *
     * @param path Path of the attribute that needs to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    fun convert(path: String, startPoint: Path<out Any>): String
}
