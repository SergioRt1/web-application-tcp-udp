package edu.escuelaing.arem.exercise4;

import java.util.concurrent.CompletableFuture;

public abstract class Exercise4 {
    public Exercise4(String message, ServerConnection serverConnection, int port) {
        System.out.println(message);
        ClientConnection clientConnection = new ClientConnection(port);
        try {
            CompletableFuture.runAsync(serverConnection::serverUp);
            Thread.sleep(200);
            clientConnection.clientUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
