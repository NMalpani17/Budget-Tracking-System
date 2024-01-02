package edu.neu.csye6200.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> readFromFile(String filePath) {
        List<String> stringsList = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                stringsList.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
        return stringsList;
    }
}
