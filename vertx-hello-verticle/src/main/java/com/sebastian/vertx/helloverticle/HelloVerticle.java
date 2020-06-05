package com.sebastian.vertx.helloverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Ávila A.
 */
public class HelloVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloVerticle.class);
    private long counter = 1;
    private static Vertx vertx;

    static {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }

    public static void main(String[] args) {
        vertx = Vertx.vertx(); // Vertx class methods is thread-safe
        vertx.deployVerticle(new HelloVerticle());
    }

    @Override
    public void start() {
        // al lanzarlo dentro de un thread no quedan todos juntos, se generan diferentes hilos
        vertx.setTimer(5000, id -> {
            LOGGER.info("timer " + id);
        });
        vertx.setPeriodic(5000, id -> {
            LOGGER.info("periodic " + id);
        });
        vertx.createHttpServer()
                .requestHandler(req -> {
                    LOGGER.info("Request #{} from {}", counter++, req.remoteAddress().host());
                    req.response().end("Hello!");
                    if (counter == 10) {
                        vertx.close();
                    }
                })
                .listen(8080);

        LOGGER.info("escuchando http://localhost:8080/");
    }
}
