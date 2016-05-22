package pl.edu.uj.kimage.eventbus;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;


public abstract class AbstractEventBusTest {
    public static final String EXPECTED_RESULT1 = "EXPECTED_RESULT1";
    public static final String EXPECTED_RESULT2 = "EXPECTED_RESULT2";
    protected EventBus eventBus;

    @Before
    public abstract void setUp();

    @Test
    public void shouldPublishEventToSubscribers() throws Exception {
        //Given
        FutureTask<String> futureTask1 = new FutureTask<>(() -> EXPECTED_RESULT1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> EXPECTED_RESULT2);
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
    public void shouldPublishEventToParents() throws Exception {
        // given
        FutureTask<String> futureTask = new FutureTask<>(() -> EXPECTED_RESULT1);
        eventBus.registerEventListener(EventWithContent.class, event -> futureTask.run());
        Event eventWithContentChild = new ChildEvent();

        // when
        eventBus.publish(eventWithContentChild);

        // then
        assertThat(futureTask.get(100, TimeUnit.MILLISECONDS)).isEqualTo(EXPECTED_RESULT1);
    }

    @Test
    public void shouldRouteOnlyToOneCommandSubscriber() throws Exception {
        //Given
        FutureTask<String> futureTask1 = new FutureTask<>(() -> EXPECTED_RESULT1);
        FutureTask<String> futureTask2 = new FutureTask<>(() -> EXPECTED_RESULT1);
        eventBus.registerCommandHandler(TestCommand.class, event -> futureTask1.run());
        eventBus.registerCommandHandler(TestCommand.class, event -> futureTask2.run());

        Command command = new TestCommand();
        eventBus.post(command);

        //When
        String result;
        try {
            result = futureTask1.get(100, TimeUnit.MILLISECONDS);

        } catch (TimeoutException timeOutException) {
            result = futureTask2.get(100, TimeUnit.MILLISECONDS);
        }

        //Then
        assertThat(result).isEqualTo(EXPECTED_RESULT1);
        assertThat(futureTask2.isDone() != futureTask1.isDone()).isEqualTo(true);
    }

    @Test
    public void shouldPostCommandToParents() throws Exception {
        // given
        FutureTask<String> futureTask = new FutureTask<>(() -> EXPECTED_RESULT1);
        eventBus.registerCommandHandler(TestCommand.class, event -> futureTask.run());
        Command testCommandChild = new ChildCommand();

        // when
        eventBus.post(testCommandChild);

        // then
        String result = futureTask.get(100, TimeUnit.MILLISECONDS);
        assertThat(result).isEqualTo(EXPECTED_RESULT1);
    }
}