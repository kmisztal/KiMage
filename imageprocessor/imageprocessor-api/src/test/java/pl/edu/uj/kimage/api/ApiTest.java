package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiTest {

    @Test
    public void shouldCorrectlySerializeApiClasses() throws JsonProcessingException {
        //Given
        StepDependency stepDependency = new StepDependency(0, Object.class);
        Step step = new Step(0, "pluginName", Arrays.asList(stepDependency));
        Task processingSchedule = new Task("1", "".getBytes(), Arrays.asList(step));
        ObjectMapper objectMapper = new ObjectMapper();
        //When
        String valueAsString = objectMapper.writeValueAsString(processingSchedule);
        //Then
        assertThat(valueAsString).matches("^\\{.*\\}$");
    }

    @Test
    public void shouldCorrectlyDeserializeApiClasses() throws IOException {
        StepDependency stepDependency = new StepDependency(0, Object.class);
        Step step = new Step(0, "pluginName", Arrays.asList(stepDependency));
        Task processingSchedule = new Task("2", "".getBytes(), Arrays.asList(step));
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(processingSchedule);
        //When
        Task readValue = objectMapper.readValue(valueAsString, Task.class);
        //Then
        assertThat(readValue).isEqualTo(processingSchedule);
    }
}