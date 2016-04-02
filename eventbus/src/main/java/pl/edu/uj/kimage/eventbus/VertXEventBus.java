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
    public void registerEventListener(final Class<? extends Event> eventClass, EventListener eventListener) {
        eventBus.consumer(toTopicName(eventClass))
                .handler(objectMessage -> {
                    Event event = messageTranslator.deserialize(eventClass, (String) objectMessage.body());
                    eventListener.receive(event);
                });
    }

    @Override
    public void registerCommandHandler(Class<? extends Command> commandClass, CommandHandler commandHandler) {
        eventBus.consumer(toTopicName(commandClass))
                .handler(objectMessage -> {
                    Command command = messageTranslator.deserialize(commandClass, (String) objectMessage.body());
                    commandHandler.receive(command);
                });
    }

    private String toTopicName(Class eventClass) {
        return eventClass.getCanonicalName();
    }
}
