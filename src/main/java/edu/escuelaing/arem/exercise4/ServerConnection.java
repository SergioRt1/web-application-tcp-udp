package edu.escuelaing.arem.exercise4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ServerConnection {
    private final int port;
    protected boolean keepAlive;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    protected OutputStream outputSteam;
    protected PrintWriter out;
    protected BufferedReader in;

    public ServerConnection(int port, boolean keepAlive) {
        this.port = port;
        this.keepAlive = keepAlive;
    }

    public void serverUp() {
        try {
            serverSocket = getServerSocket();
            do {
                getClientConnection();
                processInput();
                closeClientConnection();
            } while (keepAlive);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    protected abstract void processInput() throws IOException;

    private void getClientConnection() throws IOException {
        clientSocket = getClientSocket(serverSocket);
        outputSteam = clientSocket.getOutputStream();
        out = new PrintWriter(outputSteam, true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void closeClientConnection() {
        try {
            System.out.println("Closing connection with client.");
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection()  {
        System.out.println("Closing server connection.");
        try {
            closeClientConnection();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerSocket getServerSocket() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Starting server on port: " + port);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
        return serverSocket;
    }

    private Socket getClientSocket(ServerSocket serverSocket) {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Established connection with client");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        return clientSocket;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
}
