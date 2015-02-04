package kimage.tools.executors.gui.helpers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

/**
 *
 * @author Krzysztof
 */
public class TimeExecution {

    Instant startEvent;
    Instant stopEvent;
    Instant startJob;
    Instant stopJob;

    String currentName;

    LinkedHashMap<String, Long> timelist = new LinkedHashMap<>();

    public void startEvent() {
        startEvent = Instant.now();
    }

    public void stopEvent() {
        stopEvent = Instant.now();
    }

    public void startJob(String name) {
        currentName = name;
        startJob = Instant.now();
    }

    public void endJob(boolean print) {
        stopJob = Instant.now();
        final long v = ChronoUnit.MILLIS.between(startJob, stopJob);
        timelist.put(currentName, v);
        System.out.println(currentName + " : " + v + " ms");
    }

    public void printEventExecutionTime() {
        final long v = ChronoUnit.MILLIS.between(startEvent, stopEvent);
        timelist.put(currentName, v);
        System.out.println("Total time : " + v + " ms");
    }

}
