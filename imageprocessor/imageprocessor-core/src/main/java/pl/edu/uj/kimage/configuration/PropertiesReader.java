package pl.edu.uj.kimage.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesReader {

    private static final String CONFIG_FILE = "config.properties";

    private PropertiesReader() {
        // utility class
    }

    public static int readInt(PropertyName propertyName) {
        return Integer.valueOf(read(propertyName));
    }

    static String read(PropertyName propertyName) {
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream in = classloader.getResourceAsStream(CONFIG_FILE)) {
            properties.load(in);
            return properties.getProperty(propertyName.getKey());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unexpected property name [%s] with key [%s]. Check configuration file.",
                    propertyName.name(), propertyName.getKey()));
        }
    }
}