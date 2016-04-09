package pl.edu.uj.kimage.legacy.utils;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;

import java.util.LinkedHashMap;

/**
 * @author Krzysztof
 */
public class TimeExecution {

    long startEvent;
    long stopEvent;
    long startJob;
    long stopJob;

    String currentName;

    LinkedHashMap<String, Long> timelist = new LinkedHashMap<>();

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
        stopJob = System.currentTimeMillis();
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
