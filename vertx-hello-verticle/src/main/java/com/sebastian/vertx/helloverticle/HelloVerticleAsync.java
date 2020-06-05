package com.sebastian.vertx.helloverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Sebastián Ávila A. */
public class HelloVerticleAsync extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelloVerticleAsync.class);
  private long counter = 1;
  private static Vertx vertx;

  static {
    System.setProperty(
        "vertx.logger-delegate-factory-class-name",
        "io.vertx.core.logging.SLF4JLogDelegateFactory");
  }

  public static void main(String[] args) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new HelloVerticleAsync());
  }
  /*
  - A promise is used to write an asynchronous result
  - Future is used to view an asynchronous result
  */
  @Override
  public void start(Promise<Void> promise) {
    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              LOGGER.info("Request #{} from {}", counter++, req.remoteAddress().host());
              req.response().end("Hello!");
            })
        .listen(
            8080,
            asyncResult -> {
              if (asyncResult.succeeded()) {
                LOGGER.info("exito");
                promise.complete();
              } else {
                LOGGER.info("error");
                promise.fail(asyncResult.cause());
              }
            });
    LOGGER.info("escuchando http://localhost:8080/");
  }
}
