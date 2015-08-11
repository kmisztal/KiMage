package kimage.plugin.thread;

/**
 *
 * @author Krzysztof
 */
public interface ConcurrencyReady {

    /**
     * wartość koniecznego "brzegu" w przypadku przetwarzania równoległego np. w
     * filtrach splotowych to promień kernela lub 1/2 wysokość elementu
     * strukturalnego
     *
     * @return
     */
    public int getBoundaryForThreads();
}
