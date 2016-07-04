package pl.edu.uj.kimage.configuration;

import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

@ThreadSafe
public class ConfigurationReader {
    private static final Logger logger = LogManager.getLogger(ConfigurationReader.class);
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
            Path configurationPath = getConfigurationPath(fileName);
            List<String> lines = Files.readAllLines(configurationPath);
            for (String line : lines) {
                String[] splitted = line.split(SEPARATOR);
                builder.put(splitted[KEY_INDEX], splitted[VALUE_INDEX]);
            }
            return builder.build();

        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getConfigurationPath(String fileName) throws URISyntaxException {
        Path path;
        Path workingDirConfigurationPath = Paths.get(fileName);
        if(Files.exists(workingDirConfigurationPath)){
            path = workingDirConfigurationPath;
        }else{
            path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        }
        return path;
    }

    private void registerWatchService(String pathToFile) {
        Runnable runnable = () -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                Path configurationPath = getConfigurationPath(pathToFile).toAbsolutePath().getParent();
                configurationPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.endsWith(pathToFile)) {
                            logger.info("Configuration updated");
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