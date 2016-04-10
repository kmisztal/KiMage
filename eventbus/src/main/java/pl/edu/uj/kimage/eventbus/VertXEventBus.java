package pl.edu.uj.kimage.eventbus;

import io.vertx.core.Vertx;

public class VertXEventBus implements EventBus {

    private final io.vertx.core.eventbus.EventBus eventBus;
    private final MessageTranslator messageTranslator;

    public VertXEventBus(MessageTranslator messageTranslator) {
        this.eventBus = Vertx.vertx().eventBus();
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
    public <T extends Event> void registerEventListener(final Class<T> eventClass, EventListener<T> eventListener) {
        eventBus.consumer(toTopicName(eventClass))
                .handler(objectMessage -> {
                    T event = messageTranslator.deserialize(eventClass, (String) objectMessage.body());
                    eventListener.receive(event);
                });
    }

    @Override
    public <T extends Command> void registerCommandHandler(Class<T> commandClass, CommandHandler<T> commandHandler) {
        eventBus.consumer(toTopicName(commandClass))
                .handler(objectMessage -> {
                    T command = messageTranslator.deserialize(commandClass, (String) objectMessage.body());
                    commandHandler.receive(command);
                });
    }

    private String toTopicName(Class messageClass) {
        return messageClass.getCanonicalName();
    }
}
