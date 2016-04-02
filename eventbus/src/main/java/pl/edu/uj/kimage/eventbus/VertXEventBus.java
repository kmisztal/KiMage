package pl.edu.uj.kimage.eventbus;

public class VertXEventBus implements EventBus {

    private final io.vertx.core.eventbus.EventBus eventBus;
    private final MessageTranslator messageTranslator;

    public VertXEventBus(io.vertx.core.eventbus.EventBus eventBus, MessageTranslator messageTranslator) {
        this.eventBus = eventBus;
        this.messageTranslator = messageTranslator;
    }

    @Override
    public void publish(Event event) {
        String canonicalName = toTopicName(event.getClass());
        eventBus.publish(canonicalName, messageTranslator.serialize(event));
    }

    @Override
    public void post(Command command) {
        String canonicalName = toTopicName(command.getClass());
        eventBus.send(canonicalName, messageTranslator.serialize(command));
    }

    @Override
    public void registerEventListener(final Class<? extends Event> eventClass, EventListener eventListener) {
        eventBus.consumer(toTopicName(eventClass))
                .handler(objectMessage -> {
                    Event event = messageTranslator.deserialize(eventClass, (String) objectMessage.body());
                    eventListener.receive(event);
                });
    }

    @Override
    public void registerCommandHandler(Class<? extends Command> commandClass, CommandListener commandListener) {
        registerEventListener(commandClass, (EventListener) commandListener);
    }

    private String toTopicName(Class<? extends Event> eventClass) {
        return eventClass.getCanonicalName();
    }
}