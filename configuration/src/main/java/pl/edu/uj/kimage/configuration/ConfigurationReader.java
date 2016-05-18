package pl.edu.uj.kimage.configuration;

import com.google.common.collect.ImmutableMap;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

@ThreadSafe
public class ConfigurationReader {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String SEPARATOR = "=";
    private static ConfigurationReader INSTANCE;
    private static ImmutableMap<String, String> SETTINGS;

    private ConfigurationReader(String path) {
        SETTINGS = loadConfig(path);
        registerWatchService(path);
    }

    /**
     * @param path path to properties file in resource folder
     */
    public static ConfigurationReader getInstance(String path) {
        if (INSTANCE == null) {
            synchronized (ConfigurationReader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigurationReader(path);
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

    private void registerWatchService(String pathToFile) {
        Runnable runnable = () -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                Path path1 = Paths.get(ClassLoader.getSystemResource(pathToFile).toURI()).getParent();
                path1.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.endsWith(pathToFile)) {
                            // TODO logging
                            loadConfig(pathToFile);
                        }
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException | URISyntaxException e) {
                throw new IllegalStateException(e);
            }
        };
        new Thread(runnable).start();
    }

    public String read(String settingName) {
        return SETTINGS.get(settingName);
    }
}