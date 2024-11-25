package org.example;

import org.example.serializers.IOStreams;
import org.example.serializers.JavaSerializer;
import org.example.serializers.JsonSerializer;
import org.example.serializers.YamlSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Product> products = generateProducts();

        IOStreams.writeProductsToFile(products, "productsIO.ser");
        List<Product> deserializedProducts = IOStreams.readProductsFromFile("productsIO.ser");
        System.out.println("I/O Streams Deserialized Products: " + deserializedProducts);

        var suppliers = products.stream().flatMap(product -> product.getSuppliers().stream()).toList();
        IOStreams.writeSuppliersToFile(suppliers, "suppliers.txt");
        List<Supplier> deserializedSuppliers = IOStreams.readSuppliersFromFile("suppliers.txt");
        System.out.println("I/O Streams Deserialized Suppliers: " + deserializedSuppliers);

        // Java Native Serialization
        JavaSerializer.serializeObjects(products, "products.ser");
        List<Product> javaDeserializedProducts = JavaSerializer.deserializeObjects("products.ser");
        System.out.println("Java Deserialized Products: " + javaDeserializedProducts);

        // JSON Serialization
        JsonSerializer.serializeObjectsToJson(products, "products.json");
        List<Product> jsonDeserializedProducts = JsonSerializer.deserializeObjectsFromJson("products.json", Product[].class);
        System.out.println("JSON Deserialized Products: " + jsonDeserializedProducts);

        // YAML Serialization
        YamlSerializer.serializeObjects(products, "products.yaml");
        List<Product> yamlDeserializedProducts = YamlSerializer.deserializeObjects("products.yaml", Product[].class);
        System.out.println("YAML Deserialized Products: " + yamlDeserializedProducts);
    }

    public static List<Product> generateProducts() {
        Supplier supplier1 = new Supplier("SupplyCo", new Date());
        Supplier supplier2 = new Supplier("GlobalGoods", new Date());
        Supplier supplier3 = new Supplier("MachineMart", new Date());

        Product product1 = new Product("Espresso Machine", 1200, List.of(supplier1));
        Product product2 = new Product("Coffee Grinder", 150, Arrays.asList(supplier1, supplier2));
        Product product3 = new Product("Milk Frother", 50, List.of(supplier2));
        Product product4 = new Product("Automatic Brewer", 1800, Arrays.asList(supplier1, supplier3));
        Product product5 = new Product("Water Filter", 25, List.of(supplier3));

        return List.of(product1, product2, product3, product4, product5);
    }
}