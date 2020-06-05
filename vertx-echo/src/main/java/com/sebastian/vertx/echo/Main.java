package com.sebastian.vertx.echo;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

/**
 *
 * @author Sebastián Ávila A.
 */
public class Main {

    private static int nc = 0; // es ejecutado en un solo hilo x eso no es un atomic integer

    public static void main(String[] args) {
        final var delay = 5000;
        final var vertx = Vertx.vertx();
        vertx.createNetServer()
                .connectHandler(Main::nuevoCliente)
                .listen(3000);
        vertx.setPeriodic(delay, timerId -> System.out.println(timerId + "> " + cuantos()));
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end(cuantos())).listen(8080); // podría ser el 3000
    }

    private static void nuevoCliente(NetSocket socket) {
        nc++;
        socket.handler(buffer -> {
            socket.write(buffer);
            final var bff = buffer.toString().trim();
            if (bff.equals("quit")) {
                socket.close();
            }
        });
        socket.closeHandler(v -> {
            System.out.println("cerrado - " + v);
            nc--;
        });
    }

    private static String cuantos() {
        return "conectados: " + nc;
    }
}
