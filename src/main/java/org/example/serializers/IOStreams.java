package org.example.serializers;

import org.example.Product;
import org.example.Supplier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IOStreams {
    public static void writeProductsToFile(List<Product> products, String fileName) throws IOException {
        Path path = Paths.get("src/main/resources", fileName);
        Files.createDirectories(path.getParent());

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeInt(products.size());
            for (Product product : products) {
                // avoids product name serialization
                Product productCopy = new Product(product.getName(), 0, product.getSuppliers());
                oos.writeObject(productCopy);
            }
        }
    }

    public static List<Product> readProductsFromFile(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get("src/main/resources", fileName);

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            if (Files.size(path) == 0) {
                throw new EOFException("Empty file.");
            }
            int productCount = ois.readInt();
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < productCount; i++) {
                products.add((Product) ois.readObject());
            }
            return products;
        }
    }


    public static void writeSuppliersToFile(List<Supplier> suppliers, String fileName) throws IOException {
        Path path = Paths.get("src/main/resources", fileName);
        Files.createDirectories(path.getParent());

        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(suppliers);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Supplier> readSuppliersFromFile(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get("src/main/resources", fileName);

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (List<Supplier>) ois.readObject();
        }
    }
}
