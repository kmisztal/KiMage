package pl.edu.uj.kimage.eventbus;

public class VertXEventBus implements EventBus {

    private final io.vertx.core.eventbus.EventBus eventBus;

    public VertXEventBus(io.vertx.core.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publish(Event event) {
        String canonicalName = toTopicName(event.getClass());
        eventBus.publish(canonicalName, event);
    }

    @Override
    public void post(Command command) {

    }

    @Override
    public void register(Class<? extends Event> eventClass, EventListener eventListener) {
        eventBus.consumer(toTopicName(eventClass))
                .handler(objectMessage -> eventListener.receive((Event) objectMessage.body()));
    }

    @Override
    public void register(Class<? extends Command> eventClass, CommandListener eventListener) {

    }

    private String toTopicName(Class<? extends Event> eventClass) {
        return eventClass.getCanonicalName();
    }
}
