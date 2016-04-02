package pl.edu.uj.kimage.eventbus;

import org.junit.Before;

public class VertXEventBusTest extends AbstractEventBusTest {
    @Before
    @Override
    public void setUp(){
        eventBus = new VertXEventBus(new JsonMessageTranslator());
    }
}
