package br.com.dillmann.dynamicquery.specification.exception

/**
 * [SpecificationException] specialization for the scenarios where an unknown attribute was informed in the DSL
 *
 * @property path Path that was informed that wasn't recognized
 * @param cause Exception thrown while trying to resolve the target JPA's attribute path
 */
class UnknownAttributeNameException(val path: String, cause: Exception):
    SpecificationException("Unknown attribute name [$path]", cause)
