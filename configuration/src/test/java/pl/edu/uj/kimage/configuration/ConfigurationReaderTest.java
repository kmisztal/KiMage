package pl.edu.uj.kimage.configuration;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConfigurationReaderTest {

    private static final SettingName VALID_INT_PROPERTY_NAME = SettingName.NUMBER_OF_THREADS;
    private static final String TEST_CONFIG_FILE = "testConfig.properties";
    private static final List<String> VALID_CONFIG_FIRST_LINE = Lists.newArrayList("NUMBER_OF_THREADS=2");
    private static final String UPDATED_CONFIG_FIRST_LINE = "NUMBER_OF_THREADS=newValue";
    private static final String UPDATED_CONFIG_FIRST_LINE_VALUE = "newValue";

    @Test
    public void readValidStringPropertyWithoutException() {
        // all properties are stored as text
        ConfigurationReader configurationReader = ConfigurationReader.getInstance();
        configurationReader.read(VALID_INT_PROPERTY_NAME);
    }

    @Test
    public void readValidIntPropertyWithoutExceptions() {
        ConfigurationReader configurationReader = ConfigurationReader.getInstance();
        configurationReader.readInt(VALID_INT_PROPERTY_NAME);
    }

    @Test
    public void updateCachedPropertiesWithoutExceptions() {
        // given
        ConfigurationReader configurationReader = ConfigurationReader.getInstance();
        String oldValue = configurationReader.read(VALID_INT_PROPERTY_NAME);
        String newValue = "newValue";

        // when
        configurationReader.update(VALID_INT_PROPERTY_NAME, newValue);

        // then
        String updatedValue = configurationReader.read(VALID_INT_PROPERTY_NAME);
        configurationReader.update(VALID_INT_PROPERTY_NAME, oldValue);

        assertEquals(newValue, updatedValue);
    }

    @Test
    public void updateConfigFileWithoutExceptions() {
        // given
        ConfigurationReader configurationReader = ConfigurationReader.getInstance();
        ImmutableMap<SettingName, String> properties = configurationReader.loadConfig(TEST_CONFIG_FILE);
        String oldValue = properties.get(VALID_INT_PROPERTY_NAME);
        List<String> newProperties = Lists.newArrayList(UPDATED_CONFIG_FIRST_LINE);

        // when
        configurationReader.updateConfig(TEST_CONFIG_FILE, newProperties);
        ImmutableMap<SettingName, String> updatedProperties = configurationReader.loadConfig(TEST_CONFIG_FILE);

        // then
        String updatedProperty = updatedProperties.get(VALID_INT_PROPERTY_NAME);
        configurationReader.updateConfig(TEST_CONFIG_FILE, VALID_CONFIG_FIRST_LINE);

        assertEquals(UPDATED_CONFIG_FIRST_LINE_VALUE, updatedProperty);
        assertNotEquals(oldValue, updatedProperty);
    }
}