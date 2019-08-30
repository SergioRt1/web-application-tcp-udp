package edu.escuelaing.arem.exercise1;

import java.net.MalformedURLException;
import java.net.URL;

public class Exercise1 {
    public Exercise1() {
        System.out.println("\n\nExercise 1");
        try {
            URL google = new URL("http://www.google.com:80/index.html?queryParam=10#home");
            System.out.println("getProtocol: " + google.getProtocol());
            System.out.println("getAuthority: " + google.getAuthority());
            System.out.println("getHost: " + google.getHost());
            System.out.println("getPort: " + google.getPort());
            System.out.println("getPath: " + google.getPath());
            System.out.println("getQuery: " + google.getQuery());
            System.out.println("getFile: " + google.getFile());
            System.out.println("getRef: " + google.getRef());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
