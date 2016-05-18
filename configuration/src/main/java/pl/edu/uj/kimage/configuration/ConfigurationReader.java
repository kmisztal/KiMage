package pl.edu.uj.kimage.configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ThreadSafe
public class ConfigurationReader {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String SEPARATOR = "=";
    private static ConfigurationReader INSTANCE;
    private static ImmutableMap<String, String> SETTINGS;
    private static String PATH;

    private ConfigurationReader(String path) {
        SETTINGS = loadConfig(path);
    }

    /**
     * @param path path to properties file in resource folder
     */
    public static ConfigurationReader getInstance(String path) {
        PATH = path;
        if (INSTANCE == null) {
            synchronized (ConfigurationReader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigurationReader(PATH);
                }
            }
        }
        return INSTANCE;
    }

    ImmutableMap<String, String> loadConfig(String fileName) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] splitted = line.split(SEPARATOR);
                builder.put(splitted[KEY_INDEX], splitted[VALUE_INDEX]);
            }
            return builder.build();

        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void update(String settingName, String newValue) {
        List<String> propertiesLines = Lists.newArrayList();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        ImmutableMap<String, String> configToUpdate = loadConfig(PATH);
        ImmutableSet<String> settingsSet = configToUpdate.keySet();
        if (settingsSet.size() == 0) {
            builder.put(settingName, newValue);
            propertiesLines.add(settingName + SEPARATOR + newValue);
        } else {
            for (String name : settingsSet) {
                if (name.equals(settingName)) {
                    builder.put(settingName, newValue);
                    propertiesLines.add(settingName + SEPARATOR + newValue);
                } else {
                    String value = SETTINGS.get(name);
                    builder.put(name, value);
                    propertiesLines.add(name + SEPARATOR + newValue);
                }
            }
        }

        SETTINGS = builder.build();
        updateConfig(PATH, propertiesLines);
    }

    void updateConfig(String fileName, List<String> properties) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            Files.write(path, properties, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String read(String settingName) {
        return SETTINGS.get(settingName);
    }
}