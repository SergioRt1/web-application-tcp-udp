package edu.escuelaing.arem.exercise4.exercise431;

import edu.escuelaing.arem.exercise4.ServerConnection;

import java.io.IOException;

public class ServerConnection431 extends ServerConnection {
    public ServerConnection431(int port) {
        super(port, false);
    }

    @Override
    protected void processInput() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Server received input from client: " + inputLine);
            String response;
            try {
                Integer number = Integer.valueOf(inputLine);
                response = Integer.toString(number * number);
            } catch (NumberFormatException e) {
                response = "Error the provided input is not a number: "+ inputLine;
                System.err.println(response);
            }
            out.println(response);
        }
    }
}
