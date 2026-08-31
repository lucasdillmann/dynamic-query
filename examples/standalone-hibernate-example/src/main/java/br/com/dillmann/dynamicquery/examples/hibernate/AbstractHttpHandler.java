package br.com.dillmann.dynamicquery.examples.hibernate;

import br.com.dillmann.dynamicquery.DynamicQuery;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractHttpHandler<T> implements HttpHandler {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();
    private final Class<T> entityClass;
    private final EntityManager entityManager;

    protected AbstractHttpHandler(final EntityManager entityManager, final Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    protected Predicate staticPredicate(
        final Root<T> root,
        final CriteriaQuery<T> query,
        final CriteriaBuilder builder
    ) {
        return null;
    }

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        final var params = parseQuery(exchange.getRequestURI().getRawQuery());
        final var queryExpression = params.get("query");

        final var builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        final Root<T> root = criteriaQuery.from(entityClass);

        final var staticPredicate = staticPredicate(root, criteriaQuery, builder);

        if (queryExpression != null && !queryExpression.isBlank()) {
            final var specification = DynamicQuery.parse(queryExpression);
            final var dynamicPredicate = specification.toPredicate(root, criteriaQuery, builder);
            final var finalPredicate = staticPredicate == null
                ? dynamicPredicate
                : builder.and(staticPredicate, dynamicPredicate);

            criteriaQuery.where(finalPredicate);
        } else if (staticPredicate != null) {
            criteriaQuery.where(staticPredicate);
        }

        final var results = entityManager.createQuery(criteriaQuery).getResultList();
        final var responseBytes = jsonMapper.writeValueAsBytes(results);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        try (final var os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private Map<String, String> parseQuery(final String query) {
        final var params = new LinkedHashMap<String, String>();
        if (query == null || query.isBlank()) {
            return params;
        }

        for (final var param : query.split("&")) {
            final var entry = param.split("=", 2);
            final var key = URLDecoder.decode(entry[0], StandardCharsets.UTF_8);
            final var value = entry.length > 1
                ? URLDecoder.decode(entry[1], StandardCharsets.UTF_8)
                : null;
            params.put(key, value);
        }

        return params;
    }
}
