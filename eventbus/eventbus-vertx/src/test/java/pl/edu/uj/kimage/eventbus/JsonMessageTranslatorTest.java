package pl.edu.uj.kimage.eventbus;

import org.junit.Before;

public class JsonMessageTranslatorTest extends MessageTranslatorTest {

    @Before
    public void setUp() throws Exception {
        messageTranslator = new JsonMessageTranslator();
    }
}
