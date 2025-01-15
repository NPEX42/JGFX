package dev.npex42.npcore;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class IO {
    public static String ReadText(String filepath) throws RuntimeException {
        try {
            return Files.readString(Paths.get(filepath));
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }
    public static List<String> ReadLines(String filepath) throws RuntimeException {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    public static void WriteText(String filepath, String string) throws RuntimeException {
        try {
            Files.writeString(Paths.get(filepath), string, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }
}
