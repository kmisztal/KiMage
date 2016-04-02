package pl.edu.uj.kimage.eventbus;

public interface CommandListener {
    void receive(Command command);
}
