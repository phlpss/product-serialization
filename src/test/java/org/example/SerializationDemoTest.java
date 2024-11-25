//package org.example;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//
//class SerializationDemoTest {
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//
//    @BeforeEach
//    void setUpStreams() {
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @AfterEach
//    void restoreStreams() {
//        System.setOut(originalOut);
//    }
//
//    @Test
//    void testJavaSerializationAndDeserialization() throws IOException, ClassNotFoundException {
//        Product originalProduct = new Product("Espresso Machine", 1200,
//                new ProductDetails("CoffeeBrew", new Date()));
//        Path tempFile = Files.createTempFile("product", ".ser");
//
//        // Serialize
//        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
//            mockedFiles.when(() -> Files.newOutputStream(any(Path.class)))
//                    .thenReturn(Files.newOutputStream(tempFile.toFile().toPath()));
//            SerializationDemo.serializeJavaObject(originalProduct);
//        }
//
//        // Deserialize
//        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
//            mockedFiles.when(() -> Files.newInputStream(any(Path.class)))
//                    .thenReturn(Files.newInputStream(tempFile.toFile().toPath()));
//            Product deserializedProduct = SerializationDemo.deserializeJavaObject();
//            assertNotNull(deserializedProduct);
//            assertEquals("Espresso Machine", deserializedProduct.getName());
//            assertEquals(0, deserializedProduct.getPrice()); // since price is transient
//        }
//
//        Files.deleteIfExists(tempFile);
//    }
//
//    @Test
//    void testGsonSerializationAndDeserialization() throws IOException {
//        Product originalProduct = new Product("Espresso Machine", 1200,
//                new ProductDetails("CoffeeBrew", new Date()));
//        File tempFile = File.createTempFile("product", ".json");
//
//        // Serialize
//        SerializationDemo.serializeWithGson(originalProduct);
//        assertTrue(tempFile.exists());
//
//        // Deserialize and check output
//        SerializationDemo.deserializeWithGson();
//        assertTrue(outContent.toString().contains("Espresso Machine"));
//
//        tempFile.deleteOnExit();
//    }
//
//    @Test
//    void testYamlSerializationAndDeserialization() throws IOException {
//        Product originalProduct = new Product("Espresso Machine", 1200,
//                new ProductDetails("CoffeeBrew", new Date()));
//        File tempFile = File.createTempFile("product", ".yaml");
//
//        // Serialize
//        SerializationDemo.serializeWithYAML(originalProduct);
//        assertTrue(tempFile.exists());
//
//        // Deserialize and check output
//        SerializationDemo.deserializeWithYAML();
//        assertTrue(outContent.toString().contains("Espresso Machine"));
//
//        tempFile.deleteOnExit();
//    }
//}