package org.example.serializers;
import org.example.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaSerializer {
    public static <T> void serializeObjects(List<T> objects, String filename) throws IOException {
        Path resourcePath = Paths.get("src/main/resources", filename);
        Files.createDirectories(resourcePath.getParent());

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(resourcePath))) {
            if (objects != null && !objects.isEmpty() && objects.get(0) instanceof Product) {
                // making copy of product to avoid serializing suppliers
                List<Product> productCopies = new ArrayList<>();
                for (T obj : objects) {
                    Product product = (Product) obj;
                    productCopies.add(new Product(product.getName(), product.getPrice(), null));
                }
                oos.writeObject(productCopies);
            } else {
                oos.writeObject(objects);
            }
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