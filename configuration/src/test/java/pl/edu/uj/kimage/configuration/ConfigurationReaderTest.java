package pl.edu.uj.kimage.configuration;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConfigurationReaderTest {

    private static final String PATH = "testConfig.properties";
    private static final List<String> VALID_CONFIG_FIRST_LINE = Lists.newArrayList("NUMBER_OF_THREADS=2");
    private static final String UPDATED_CONFIG_FIRST_LINE = "NUMBER_OF_THREADS=newValue";
    private static final String UPDATED_CONFIG_FIRST_LINE_VALUE = "newValue";
    private static final String VALID_PROPERTY_NAME = "NUMBER_OF_THREADS";

    @Test
    public void readValidStringPropertyWithoutException() {
        // all properties are stored as text
        ConfigurationReader configurationReader = ConfigurationReader.getInstance(PATH);
        configurationReader.read(VALID_PROPERTY_NAME);
    }

    @Test
    public void updateCachedPropertiesWithoutExceptions() {
        // given
        ConfigurationReader configurationReader = ConfigurationReader.getInstance(PATH);
        String oldValue = configurationReader.read(VALID_PROPERTY_NAME);
        String newValue = "newValue";

        // when
        configurationReader.update(VALID_PROPERTY_NAME, newValue);

        // then
        String updatedValue = configurationReader.read(VALID_PROPERTY_NAME);
        configurationReader.update(VALID_PROPERTY_NAME, oldValue);

        assertEquals(newValue, updatedValue);
    }

    @Test
    public void updateConfigFileWithoutExceptions() {
        // given
        ConfigurationReader configurationReader = ConfigurationReader.getInstance(PATH);
        ImmutableMap<String, String> properties = configurationReader.loadConfig(PATH);
        String oldValue = properties.get(VALID_PROPERTY_NAME);
        List<String> newProperties = Lists.newArrayList(UPDATED_CONFIG_FIRST_LINE);

        // when
        configurationReader.updateConfig(PATH, newProperties);
        ImmutableMap<String, String> updatedProperties = configurationReader.loadConfig(PATH);

        // then
        String updatedProperty = updatedProperties.get(VALID_PROPERTY_NAME);
        configurationReader.updateConfig(PATH, VALID_CONFIG_FIRST_LINE);

        assertEquals(UPDATED_CONFIG_FIRST_LINE_VALUE, updatedProperty);
        assertNotEquals(oldValue, updatedProperty);
    }
}