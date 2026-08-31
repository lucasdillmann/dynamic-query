package br.com.dillmann.dynamicquery.examples.hibernate;

import br.com.dillmann.dynamicquery.examples.hibernate.product.ProductHttpHandler;
import br.com.dillmann.dynamicquery.examples.hibernate.productgroup.ProductGroupHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Application {

    private static final int PORT = 8080;

    public static void main(final String[] args) throws Exception {
        final var persistenceFactory = new PersistenceFactory();
        final var entityManager = persistenceFactory.getEntityManager();

        final var server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/product-groups", new ProductGroupHttpHandler(entityManager));
        server.createContext("/products", new ProductHttpHandler(entityManager));

        Runtime.getRuntime().addShutdownHook(new Thread(persistenceFactory::close));

        server.start();
        System.out.println("Server started on port " + PORT);
    }
}
