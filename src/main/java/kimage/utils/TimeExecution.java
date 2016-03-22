package kimage.utils;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Krzysztof
 */
public class TimeExecution {

    private long startEvent;
    private long stopEvent;
    private long startJob;

    private String currentName;

    private final Map<String, Long> timelist = new LinkedHashMap<>();

    public void startEvent() {
        startEvent = System.currentTimeMillis();//Instant.now();
    }

    public void stopEvent() {
        stopEvent = System.currentTimeMillis();
    }

    public void startJob(String name) {
        currentName = name;
        startJob = System.currentTimeMillis();
    }

    public void endJob(boolean print) {
        final long stopJob = System.currentTimeMillis();
        final long v = stopJob - startJob;//ChronoUnit.MILLIS.between(startJob, stopJob);
        timelist.put(currentName, v);
        System.out.println(currentName + " : " + v);
    }

    public void printEventExecutionTime() {
        final long v = stopEvent - startEvent;//ChronoUnit.MILLIS.between(startEvent, stopEvent);
        timelist.put(currentName, v);
        System.out.println("Total time : " + v);
    }

}
