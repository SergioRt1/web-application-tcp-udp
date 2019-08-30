package edu.escuelaing.arem.exercise5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeServer {
    private DatagramSocket socket;
    private final byte[] buf;
    private final int port;
    private boolean isAlive;

    public DatagramTimeServer() {
        this.port = 4445;
        this.buf = new byte[256];
        this.isAlive = true;

    }

    public void startServer() {
        try {
            this.socket = new DatagramSocket(port);
            System.out.println("Server Up");
            while (isAlive) {
                processRequest();
            }
        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Server shutdown, bye.");
            socket.close();
        }
    }

    private void processRequest() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String dString = new Date().toString();
        byte[] sendBuf = dString.getBytes();
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(packet);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}