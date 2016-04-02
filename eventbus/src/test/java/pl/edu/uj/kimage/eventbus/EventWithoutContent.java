package pl.edu.uj.kimage.eventbus;

import java.util.Objects;

public class EventWithoutContent implements Event {
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.nonNull(obj) && obj.getClass()==this.getClass();
    }
}
