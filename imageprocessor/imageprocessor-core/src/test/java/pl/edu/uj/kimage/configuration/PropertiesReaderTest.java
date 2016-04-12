package pl.edu.uj.kimage.configuration;

import org.junit.Test;

public class PropertiesReaderTest {

    private static final PropertyName VALID_INT_PROPERTY_NAME = PropertyName.NUMBER_OF_THREADS;

    @Test
    public void readValidStringProperty() {
        // all properties are stored as text
        PropertiesReader.read(VALID_INT_PROPERTY_NAME);
    }


    @Test
    public void readValidIntPropertyWithoutExceptions() {
        PropertiesReader.readInt(VALID_INT_PROPERTY_NAME);
    }
}