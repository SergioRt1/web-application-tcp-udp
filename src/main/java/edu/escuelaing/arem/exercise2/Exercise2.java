package edu.escuelaing.arem.exercise2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Exercise2 {
    public Exercise2() {
        System.out.println("\n\nExercise 2");
        System.out.print("Input an URL: ");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {
            String urlString = buffer.readLine();
            BufferedReader response = readFromURL(urlString);
            saveInFile(response);
            System.out.println("File successfully save as result.html in the root of the project.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedReader readFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);

        return new BufferedReader(new InputStreamReader(url.openStream()));
    }


    private static void saveInFile(BufferedReader reader) throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        Path filePath = Paths.get("result.html");
        File result = new File("result.html");
        if (result.exists()) {
            result.delete();
        }

        result.createNewFile();
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }
}
