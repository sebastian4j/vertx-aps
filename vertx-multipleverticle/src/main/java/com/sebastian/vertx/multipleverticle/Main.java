package com.sebastian.vertx.multipleverticle;

import io.vertx.core.Vertx;

/** 
 * - By default Vert.x creates twice the number of event-loop threads than CPU core
 * - The assignment of verticles to event-loops is done in a round-robin fashion
 * - A verticle always uses the same event-loop thread
 * - The event-loop threads are being shared by multiple verticles
 */
public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Deployer());
  }
}
