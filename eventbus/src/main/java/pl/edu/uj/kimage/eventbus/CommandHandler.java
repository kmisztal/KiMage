package pl.edu.uj.kimage.eventbus;

public interface CommandHandler {
    void receive(Command command);
}
