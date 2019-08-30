package edu.escuelaing.arem.exercise4.exercise432;

import edu.escuelaing.arem.exercise4.ServerConnection;

import java.io.IOException;
import java.util.StringTokenizer;

public class ServerConnection432 extends ServerConnection {
    private Function function;

    public ServerConnection432(int port) {
        super(port, false);
        function = Math::cos;
    }

    @Override
    protected void processInput() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Server received input from client: " + inputLine);
            StringTokenizer stringTokenizer = new StringTokenizer(inputLine, ":");
            String input = stringTokenizer.nextToken();
            String response;
            if (isFunction(input)) {
                response = changeFunction(stringTokenizer.nextToken());
            } else {
                response = applyFunction(input);
            }

            out.println(response);
        }
    }

    private String applyFunction(String input) {
        String response;
        try {
            double number = Double.valueOf(input);
            response = Double.toString(function.apply(number));
        } catch (NumberFormatException e) {
            response = "Error the provided input is not a number: " + input;
            System.err.println(response);
        }

        return response;
    }

    private boolean isFunction(String input) {
        return input.equals("fun");
    }

    private String changeFunction(String functionCode) {
        String response = "function changed to: " + functionCode;
        switch (functionCode) {
            case "sin":
                function = Math::sin;
                break;
            case "cos":
                function = Math::cos;
                break;
            case "tan":
                function = Math::tan;
                break;
            default:
                response = functionCode + " is not a valid function code.";
                System.err.println(response);
        }

        return response;
    }

    @FunctionalInterface
    private interface Function {
        double apply(double value);
    }

}
