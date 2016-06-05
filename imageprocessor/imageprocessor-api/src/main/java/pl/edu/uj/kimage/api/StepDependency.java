package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StepDependency  implements Serializable {
    private final int dependentStepNumber;
    private final String eventTypeName;

    @JsonCreator
    public StepDependency(@JsonProperty("dependentStepNumber") int dependentStepNumber, @JsonProperty("eventTypeName") String eventTypeName) {
        this.dependentStepNumber = dependentStepNumber;
        this.eventTypeName = eventTypeName;
    }

    public StepDependency(int dependentStepNumber, Class aClass) {
        this(dependentStepNumber, aClass.getName());
    }

    public int getDependentStepNumber() {
        return dependentStepNumber;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepDependency that = (StepDependency) o;

        if (dependentStepNumber != that.dependentStepNumber) return false;
        return eventTypeName != null ? eventTypeName.equals(that.eventTypeName) : that.eventTypeName == null;

    }

    @Override
    public int hashCode() {
        int result = dependentStepNumber;
        result = 31 * result + (eventTypeName != null ? eventTypeName.hashCode() : 0);
        return result;
    }
}
