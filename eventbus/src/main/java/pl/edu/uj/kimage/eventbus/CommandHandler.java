package pl.edu.uj.kimage.eventbus;

public interface CommandHandler<T extends Command> {
    void receive(T command);
}
