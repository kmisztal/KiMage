package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.*;

public class TestEventBus implements EventBus {
    @Override
    public void publish(Event event) {
        //TODO this is not finished
    }

    @Override
    public void post(Command command) {
        //TODO this is not finished
    }

    @Override
    public void registerEventListener(Class<? extends Event> eventClass, EventListener<Event> eventListener) {
        //TODO this is not finished
    }

    @Override
    public void registerCommandHandler(Class<? extends Command> eventClass, CommandHandler<Command> commandHandler) {
        //TODO this is not finished
    }
}
