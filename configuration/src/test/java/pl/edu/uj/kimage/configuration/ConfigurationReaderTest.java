package pl.edu.uj.kimage.configuration;


import org.junit.Test;

public class ConfigurationReaderTest {

    private static final String PATH = "testConfig.properties";
    private static final String VALID_PROPERTY_NAME = "NUMBER_OF_THREADS";

    @Test
    public void readValidStringPropertyWithoutException() {
        // all properties are stored as text
        ConfigurationReader configurationReader = ConfigurationReader.getInstance(PATH);
        configurationReader.read(VALID_PROPERTY_NAME);
    }
}