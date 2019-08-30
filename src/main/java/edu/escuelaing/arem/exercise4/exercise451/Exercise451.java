package edu.escuelaing.arem.exercise4.exercise451;

import edu.escuelaing.arem.exercise4.ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Exercise451 {

    private final ServerConnection serverConnection;
    private AtomicBoolean isAlive;

    public Exercise451() {
        System.out.println("\n\nExercise 4.5.1\nTo exit type 'quit' via standard input.");
        isAlive = new AtomicBoolean(true);
        CompletableFuture.runAsync(this::processInputFromCL);
        serverConnection = new ServerConnection451(35000);
        serverConnection.serverUp();
    }

    public void processInputFromCL() {
        BufferedReader inputFromCL = new BufferedReader(new InputStreamReader(System.in));
        while (isAlive.get()) {
            try {
                String command = inputFromCL.readLine().trim();
                switch (command) {
                    case "quit":
                        this.serverConnection.setKeepAlive(false);
                        isAlive.set(false);
                        System.out.println("If the server not end just send a request.");
                        break;
                    default:
                        System.err.println("Invalid command, try with 'quit'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
