package pl.edu.uj.kimage.eventbus;

public interface EventBus {
    void publish(Event event);
    void post(Command command);
    void register(Class<? extends Event> eventClass, EventListener eventListener);
    void register(Class<? extends Command> eventClass, CommandListener eventListener);
}
