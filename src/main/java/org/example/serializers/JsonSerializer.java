package org.example.serializers;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class JsonSerializer {
//    public static <T> void serializeObjectsToJson(List<T> objects, String filename) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File(filename), objects);
//    }
//
//    public static <T> List<T> deserializeObjectsFromJson(String filename, Class<T[]> clazz) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        T[] array = mapper.readValue(new File(filename), clazz);
//        return List.of(array);
//    }
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonSerializer {
    public static <T> void serializeObjectsToJson(List<T> objects, String filename) throws IOException {
        Path resourcePath = Paths.get("src/main/resources", filename);
        Files.createDirectories(resourcePath.getParent()); // Ensure directory exists

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resourcePath.toFile(), objects);
    }

    public static <T> List<T> deserializeObjectsFromJson(String filename, Class<T[]> clazz) throws IOException {
        Path resourcePath = Paths.get("src/main/resources", filename);

        ObjectMapper mapper = new ObjectMapper();
        T[] array = mapper.readValue(resourcePath.toFile(), clazz);
        return List.of(array);
    }
}