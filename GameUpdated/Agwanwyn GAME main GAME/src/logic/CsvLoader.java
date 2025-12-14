package logic;

import model.SyllableTask;

import java.io.*;
import java.util.*;

public class CsvLoader {

    public static List<SyllableTask> loadTasks(String filePath) {
        List<SyllableTask> tasks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String first = parts[0].trim();
                    String second = parts[1].trim();
                    String command = parts[2].trim();

                    tasks.add(new SyllableTask(first, second, command));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
