package pl.edu.uj.kimage;

import pl.edu.uj.kimage.eventbus.Event;

public interface Plugin {
    void processEvent(Event event);
}
