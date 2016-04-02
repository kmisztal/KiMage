package pl.edu.uj.kimage.eventbus;

import io.vertx.core.Vertx;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;


public class VertXEventBusTest {
    private EventBus eventBus;

    public static final String EXPECTED_RESULT1 = "EXPECTED_RESULT1";
    public static final String EXPECTED_RESULT2 = "EXPECTED_RESULT2";

    @Before
    public void setUp() throws Exception {
        eventBus = new VertXEventBus(Vertx.vertx().eventBus(), new MessageTranslator());
    }

    @Test
    public void shouldPublishEventToSubscribers() throws Exception {
        //Given
        FutureTask<String> futureTask1 = new FutureTask<>(() -> EXPECTED_RESULT1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> EXPECTED_RESULT2);;
        eventBus.registerEventListener(EventWithContent.class, event -> futureTask1.run());
        eventBus.registerEventListener(EventWithContent.class, event -> futureTask2.run());
        EventWithContent eventWithContent = new EventWithContent();

        //When
        eventBus.publish(eventWithContent);

        //Then
        assertThat(futureTask1.get(100, TimeUnit.MILLISECONDS)).isEqualTo(EXPECTED_RESULT1);
        assertThat(futureTask2.get(100, TimeUnit.MILLISECONDS)).isEqualTo(EXPECTED_RESULT2);
    }

    @Test
    public void shouldRouteOnlyToOneCommandSubscriber() throws Exception {
        //Given
        FutureTask<String> futureTask1 = new FutureTask<>(() -> EXPECTED_RESULT1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> EXPECTED_RESULT1);
        eventBus.registerCommandHandler(TestCommand.class, event -> futureTask1.run());
        eventBus.registerCommandHandler(TestCommand.class, event -> futureTask2.run());

        Command command = new TestCommand();
        String result;
        eventBus.post(command);

        //When
        try{
            result = futureTask1.get(100,TimeUnit.MILLISECONDS);

        }catch (TimeoutException timeOutException)
        {
            result = futureTask2.get(100,TimeUnit.MILLISECONDS);
        }

        //Then
        assertThat(result).isEqualTo(EXPECTED_RESULT1);
        assertThat(futureTask2.isDone()!=futureTask1.isDone()).isEqualTo(true);
    }

}