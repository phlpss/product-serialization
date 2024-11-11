package org.example.serializers;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class JavaSerializer {
//    public static <T> void serializeObjects(List<T> objects, String filename) throws IOException {
//        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
//            oos.writeObject(objects);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> List<T> deserializeObjects(String filename) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))) {
//            return (List<T>) ois.readObject();
//        }
//    }
//}

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JavaSerializer {
    public static <T> void serializeObjects(List<T> objects, String filename) throws IOException {
        Path resourcePath = Paths.get("src/main/resources", filename);
        Files.createDirectories(resourcePath.getParent()); // Ensure directory exists

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(resourcePath))) {
            oos.writeObject(objects);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> deserializeObjects(String filename) throws IOException, ClassNotFoundException {
        Path resourcePath = Paths.get("src/main/resources", filename);

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(resourcePath))) {
            return (List<T>) ois.readObject();
        }
    }
}
