package pl.edu.uj.kimage.eventbus;

import io.vertx.core.Vertx;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class VertXEventBusTest {
    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = new VertXEventBus(Vertx.vertx().eventBus());
    }

    @Test
    public void shouldPublishEventToSubscribers() throws Exception {
        //Given
        final List<Event> results = new ArrayList<>(2);
        EventListener aEventListener = event -> {results.add(event);};
        eventBus.register(EventA.class, aEventListener);
        EventA eventA = new EventA();
        //When
        eventBus.publish(eventA);
        //Then
        assertThat(results).hasSize(1).contains(eventA);
    }
}