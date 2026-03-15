package org.example.utils;

import java.io.*;
import java.util.*;

/**
 * Utility class for reading and writing simple key-value data to a flat file.
 * <p>
 * Data is stored in {@code data.txt} as {@code key=value} pairs, one per line.
 * Used to persist the top score between sessions.
 * </p>
 */
public class FileManager {
    private static final String FILE_NAME = "data.txt";

    /**
     * Reads the value for the given key from the data file.
     *
     * @param key          the key to look up
     * @param defaultValue the value to return when the key is not found or the file cannot be read
     * @return the stored value, or {@code defaultValue} if not found
     */
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

    /**
     * Writes or updates the given key-value pair in the data file.
     * All other existing entries are preserved.
     *
     * @param key   the key to store
     * @param value the value to associate with the key
     */
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
            e.printStackTrace();
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
