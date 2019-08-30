package edu.escuelaing.arem.exercise5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Exercise5 {
    private AtomicBoolean isAlive;
    private final DatagramTimeServer serverConnection;
    private final DatagramTimeClient clientConnection;

    public Exercise5() {
        System.out.println("\n\nExercise 5\nYou can type commands via standard input ('quit', 'serverOff' or 'serverOn').");
        this.isAlive = new AtomicBoolean(true);
        clientConnection = new DatagramTimeClient();
        serverConnection = new DatagramTimeServer();
        try {
            CompletableFuture.runAsync(serverConnection::startServer);
            CompletableFuture.runAsync(this::processInputFromCL);
            Thread.sleep(200);
            clientConnection.clientUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processInputFromCL() {
        BufferedReader inputFromCL = new BufferedReader(new InputStreamReader(System.in));
        while (isAlive.get()) {
            try {
                String command = inputFromCL.readLine().trim();
                switch (command) {
                    case "quit":
                        this.clientConnection.setAlive(false);
                        this.serverConnection.setAlive(false);
                        this.isAlive.set(false);
                        break;
                    case "serverOff":
                        this.serverConnection.setAlive(false);
                        break;
                    case "serverOn":
                        this.serverConnection.setAlive(true);
                        CompletableFuture.runAsync(serverConnection::startServer);
                        break;
                    default:
                        System.err.println("Invalid command, try with 'quit', 'serverOff' or 'serverOn'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
