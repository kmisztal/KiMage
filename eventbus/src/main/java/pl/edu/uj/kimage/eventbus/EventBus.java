package pl.edu.uj.kimage.eventbus;

public interface EventBus {
    void publish(Event event);
    void post(Command command);
    void registerEventListener(Class<? extends Event> eventClass, EventListener eventListener);
    void registerCommandHandler(Class<? extends Command> eventClass, CommandHandler commandHandler);
}
