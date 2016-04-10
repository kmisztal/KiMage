package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.*;

import java.util.ArrayList;
import java.util.List;

public class TestEventBus implements EventBus {
    private final List<Event> publishedEvents = new ArrayList<>();
    private final List<Command> postedCommands = new ArrayList<>();
    private final List<EventListener> registeredEventListeners  = new ArrayList<>();
    private final List<CommandHandler> registeredCommandListeners = new ArrayList<>();

    @Override
    public void publish(Event event) {
        publishedEvents.add(event);
    }

    @Override
    public void post(Command command) {
        postedCommands.add(command);
    }

    @Override
    public <T extends Event> void registerEventListener(Class<T> eventClass, EventListener<T> eventListener) {
        registeredEventListeners.add(eventListener);
    }

    @Override
    public <T extends Command> void registerCommandHandler(Class<T> eventClass, CommandHandler<T> commandHandler) {
        registeredCommandListeners.add(commandHandler);
    }

    public List<Event> getPublishedEvents() {
        return publishedEvents;
    }

    public List<Command> getPostedCommands() {
        return postedCommands;
    }

    public List<EventListener> getRegisteredEventListeners() {
        return registeredEventListeners;
    }

    public List<CommandHandler> getRegisteredCommandListeners() {
        return registeredCommandListeners;
    }
}
