package org.example.utils;

import java.io.*;
import java.util.*;

public class FileManager {
    private static final String FILE_NAME = "data.txt";

    public static String read(String key, String defaultValue) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(key + "=")) {
                    return line.substring(key.length() + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static void write(String key, String value) {
        Map<String, String> data = readAll();
        data.put(key, value);
        saveAll(data);
    }

    private static Map<String, String> readAll() {
        Map<String, String> map = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return map;
    }

    private static void saveAll(Map<String, String> data) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
