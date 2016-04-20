package pl.edu.uj.kimage.eventbus;

public interface EventListener<T extends Event> {
    void receive(T event);
}
