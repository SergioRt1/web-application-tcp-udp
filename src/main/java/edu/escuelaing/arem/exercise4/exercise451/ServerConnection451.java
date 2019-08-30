package edu.escuelaing.arem.exercise4.exercise451;

import com.google.common.collect.ImmutableMap;
import edu.escuelaing.arem.exercise4.ServerConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection451 extends ServerConnection {
    private final ImmutableMap<String, String> supportedContent;

    public ServerConnection451(int port) {
        super(port, true);
        supportedContent = ImmutableMap.of(
                "html", "text/html",
                "js", "text/javascript",
                "png", "image/png",
                "jpeg", "image/jpeg",
                "gif", "image/gif"
        );
    }

    @Override
    protected void processInput() throws IOException {
        String inputLine;
        String[] request;
        if (in.ready() && (inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
            request = inputLine.split(" ");
        } else return;
        Path filePath = getPath(request[1]);

        Map<String, String> headers = getHeaders();
        this.keepAlive = headers.get("connection").equals("keep-alive");

        String fileExtension = getFileExtension(filePath.getFileName().toString());
        try {
            byte[] content = Files.readAllBytes(filePath);
            out.println(getHTTPHeader(fileExtension));
            outputSteam.write(content);
            outputSteam.flush();

            System.out.println("  200 Ok");
        } catch (IOException e) {
            String httpResponse = getErrorResponse();
            out.println(httpResponse);

            System.out.println("  404 No such file found.");
        }
    }

    private Path getPath(String file) {
        String absolutePath = Paths.get("").toAbsolutePath().toString();
        Path filePath = Paths.get(absolutePath, file);
        System.out.println("\nRequest received for file in path: " + filePath);

        return filePath;
    }

    private Map<String, String> getHeaders() throws IOException {
        String inputLine;
        Map<String, String> headers = new HashMap<>();
        while (in.ready() && (inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
            putHeader(headers, inputLine);
        }
        System.out.println("  with headers: " + headers);

        return headers;
    }

    private String getFileExtension(String fileName) {
        boolean startCopy = false;
        StringBuilder extension = new StringBuilder();
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (startCopy) {
                extension.append(c);
            }
            if (c == '.') {
                startCopy = true;
            }
        }

        return extension.toString();
    }

    private String getErrorResponse() {
        return "HTTP/1.1 404 OK\n"
                + "Server: Super Server AREP\n"
                + "Status: 404\n"
                + '\n'
                + "Not fount 404";
    }

    private String getHTTPHeader(String fileExtension) {
        return "HTTP/1.1 200 OK\n"
                + "Content-Type: " + supportedContent.getOrDefault(fileExtension, "text/html;charset=UTF-8")
                + "\nServer: Super Server AREP\n"
                + "Status: 200\n";
    }

    private void putHeader(Map<String, String> headers, String line) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean isFirstTime = true;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (isFirstTime && c == ':') {
                isFirstTime = false;
                i++;
            } else if (isFirstTime) {
                key.append(c);
            } else {
                value.append(c);
            }
        }

        headers.put(key.toString().toLowerCase(), value.toString().toLowerCase());
    }
}
