package br.com.dillmann.dynamicquery.springbootweb

import br.com.dillmann.dynamicquery.core.DynamicQuery
import br.com.dillmann.dynamicquery.core.DynamicQueryException
import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * [HandlerMethodArgumentResolver] implementation for the [DynamicQuerySpecification] able to automatically
 * produce specification instances using the HTTP request's query parameters
 *
 * @param configuration Configuration properties for the integration with the Spring Web APIs
 */
@Component
class DynamicQueryWebArgumentResolver(
    private val configuration: DynamicQueryWebConfigurationProperties,
): HandlerMethodArgumentResolver {

    /**
     * Checks if the implementation supports the given parameter. Returns true only when the needed parameter
     * is of the [DynamicQuerySpecification] type.
     *
     * @param parameter Details about the parameter being injected in a Spring Web's controller
     */
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        DynamicQuerySpecification::class.java == parameter.parameterType

    /**
     * Produces a [DynamicQuerySpecification] value from the HTTP request's query parameters
     *
     * @param parameter Details about the parameter being injected in a Spring Web's controller. Not used by this
     * implementation.
     * @param container Spring's MVC container. Not used by this implementation.
     * @param webRequest Details about the request being handled by Spring MVC
     * @param binderFactory Spring's argument binder factory. Not used by this implementation.
     * @throws DynamicQueryException when the expression parse fails with an error
     */
    override fun resolveArgument(
        parameter: MethodParameter,
        container: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): DynamicQuerySpecification? {
        val queryParameterName = configuration.queryParameterName ?: DEFAULT_HTTP_QUERY_PARAMETER_NAME
        val query = webRequest.getParameter(queryParameterName)?.takeIf { it.isNotBlank() } ?: return null
        return DynamicQuery.parse(query)
    }
}
