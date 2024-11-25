package org.example.serializers;

import org.example.Product;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class YamlSerializer {
    public static <T> void serializeObjects(List<T> objects, String filename) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Representer representer = new Representer(options) {
            @Override
            protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
                properties.removeIf(property -> property.getName().equals("contractStartDate"));
                return super.representJavaBean(properties, javaBean);
            }
        };
        Yaml yaml = new Yaml(representer, options);

        Path resourcePath = Paths.get("src/main/resources", filename);
        Files.createDirectories(resourcePath.getParent()); // Ensure directory exists

        try (FileWriter writer = new FileWriter(resourcePath.toFile())) {
            yaml.dump(objects, writer);
        }
    }

    public static <T> List<T> deserializeObjects(String filename, Class<T[]> clazz) {
        LoaderOptions loaderOptions = new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(Product.class.getName());
        loaderOptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(Product.class, loaderOptions));

        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + filename);
        }

        T[] array = yaml.loadAs(inputStream, clazz);
        return array != null ? new ArrayList<>(Arrays.asList(array)) : Collections.emptyList();
    }
}