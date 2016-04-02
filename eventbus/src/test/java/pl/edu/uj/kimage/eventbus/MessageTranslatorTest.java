package pl.edu.uj.kimage.eventbus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MessageTranslatorTest {
    private final MessageTranslator messageTranslator = new MessageTranslator();

    @Test
    public void shouldDeSerializeEventWithContent() throws Exception {
        //Given
        EventWithContent eventWithContent = new EventWithContent();
        //When
        EventWithContent result = messageTranslator.deserialize(eventWithContent.getClass(), messageTranslator.serialize(eventWithContent));
        //Then
        assertThat(result).isEqualTo(eventWithContent);
    }

    @Test
    public void shouldDeSerializeEventWithoutContent() throws Exception {
        //Given
        EventWithoutContent eventWithContent = new EventWithoutContent();
        //When
        EventWithoutContent result = messageTranslator.deserialize(eventWithContent.getClass(), messageTranslator.serialize(eventWithContent));
        //Then
        assertThat(result).isEqualTo(eventWithContent);
    }
}