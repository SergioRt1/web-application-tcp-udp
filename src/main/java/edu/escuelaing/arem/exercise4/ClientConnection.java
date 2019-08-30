package edu.escuelaing.arem.exercise4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection(int port) {
        this.port = port;
    }

    public void clientUp() {
        try {
            openConnection();
            processInputFromCL();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openConnection() {
        try {
            System.out.println("Starting client to port: " + port);
            socket = new Socket("127.0.0.1", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host!.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: localhost.");
            System.exit(1);
        }
    }

    private void processInputFromCL() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Write the client input (type 'quit' to finish):");
        while ((userInput = stdIn.readLine()) != null) {
            if (userInput.equals("quit")) break;
            out.println(userInput);
            System.out.println("Client received output from server: " + in.readLine());
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Closing client connection.");
        out.close();
        in.close();
        socket.close();
    }
}

