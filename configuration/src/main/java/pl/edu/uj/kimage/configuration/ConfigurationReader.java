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
    private static final ConfigurationReader INSTANCE = new ConfigurationReader();
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String CONFIG_FILE = "config.properties";
    private static final String SEPARATOR = "=";
    private static ImmutableMap<SettingName, String> SETTINGS;

    private ConfigurationReader() {
        SETTINGS = loadConfig(CONFIG_FILE);
    }

    public static ConfigurationReader getInstance() {
        return INSTANCE;
    }

    ImmutableMap<SettingName, String> loadConfig(String fileName) {
        ImmutableMap.Builder<SettingName, String> builder = ImmutableMap.<SettingName, String>builder();
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] splitted = line.split(SEPARATOR);
                builder.put(SettingName.valueOf(splitted[KEY_INDEX]), splitted[VALUE_INDEX]);
            }
            return builder.build();

        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public int readInt(SettingName settingName) {
        return Integer.parseInt(SETTINGS.get(settingName));
    }

    public synchronized void update(SettingName settingName, String newValue) {
        List<String> propertiesLines = Lists.newArrayList();
        ImmutableMap.Builder<SettingName, String> builder = ImmutableMap.<SettingName, String>builder();
        ImmutableSet<SettingName> settingsSet = SETTINGS.keySet();
        if (settingsSet.size() == 0) {
            builder.put(settingName, newValue);
            String name = settingName.name();
            propertiesLines.add(name + SEPARATOR + newValue);
        } else {
            for (SettingName name : settingsSet) {
                if (name == settingName) {
                    builder.put(settingName, newValue);
                    propertiesLines.add(settingName.name() + SEPARATOR + newValue);
                } else {
                    String value = SETTINGS.get(name);
                    builder.put(name, value);
                    propertiesLines.add(name.name() + SEPARATOR + newValue);
                }
            }
        }

        SETTINGS = builder.build();
        updateConfig(CONFIG_FILE, propertiesLines);
    }

    void updateConfig(String fileName, List<String> properties) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            Files.write(path, properties, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    String read(SettingName settingName) {
        return SETTINGS.get(settingName);
    }
}