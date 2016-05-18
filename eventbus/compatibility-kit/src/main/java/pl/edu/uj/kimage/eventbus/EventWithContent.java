package pl.edu.uj.kimage.eventbus;

public class EventWithContent implements Event {
    private String bomb;

    public String getBomb() {
        return bomb;
    }

    public void setBomb(String bomb) {
        this.bomb = bomb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventWithContent eventWithContent = (EventWithContent) o;

        return bomb != null ? bomb.equals(eventWithContent.bomb) : eventWithContent.bomb == null;

    }

    @Override
    public int hashCode() {
        return bomb != null ? bomb.hashCode() : 0;
    }
}
