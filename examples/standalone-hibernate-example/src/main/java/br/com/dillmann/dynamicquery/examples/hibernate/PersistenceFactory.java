package br.com.dillmann.dynamicquery.examples.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PersistenceFactory {
    private final EntityManagerFactory delegate;
    private final EntityManager entityManager;

    public PersistenceFactory() {
        delegate = Persistence.createEntityManagerFactory("example");
        entityManager = delegate.createEntityManager();

        entityManager.getTransaction().begin();
        seedData();
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void close() {
        entityManager.close();
        delegate.close();
    }

    private void seedData() {
        try (
            final var stream = getClass().getResourceAsStream("/init.sql");
            final var reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
        ) {
            final var statementBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                final var trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                    continue;
                }

                statementBuilder.append(line).append("\n");
                if (trimmed.endsWith(";")) {
                    entityManager.createNativeQuery(statementBuilder.toString()).executeUpdate();
                    statementBuilder.setLength(0);
                }
            }

            if (!statementBuilder.isEmpty()) {
                entityManager.createNativeQuery(statementBuilder.toString()).executeUpdate();
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
