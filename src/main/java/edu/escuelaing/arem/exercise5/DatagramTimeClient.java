package edu.escuelaing.arem.exercise5;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeClient {
    private final byte[] sendBuf;
    private final byte[] buf;
    private final int port;
    private boolean isAlive;
    private String currentDate;
    private DatagramSocket socket;

    public DatagramTimeClient() {
        this.sendBuf = new byte[256];
        this.buf = new byte[256];
        this.port = 4445;
        this.isAlive = true;
        this.currentDate = "Not available";
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendTimeRequest() {
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            socket.send(packet);

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            this.currentDate = new String(packet.getData(), 0, packet.getLength());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void clientUp() {
        while (isAlive) {
            try {
                sendTimeRequest();
                System.out.println("Current date in client: " + currentDate);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Client shutdown, bye.");
        socket.close();

    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}