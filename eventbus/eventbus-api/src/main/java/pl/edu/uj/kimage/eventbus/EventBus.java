package pl.edu.uj.kimage.eventbus;

public interface EventBus {
    void publish(Event event);
    void post(Command command);
    <T extends Event> void registerEventListener(Class<T> eventClass, EventListener<T> eventListener);
    <T extends Command> void registerCommandHandler(Class<T> eventClass, CommandHandler<T> commandHandler);
}
