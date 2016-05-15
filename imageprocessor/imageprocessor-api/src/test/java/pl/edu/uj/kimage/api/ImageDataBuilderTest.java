package pl.edu.uj.kimage.api;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ImageDataBuilderTest {
    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;

    @Test
    public void shouldBuildImageByteArray() throws Exception {
        //Given
        ImageDataBuilder dataBuilder = new ImageDataBuilder(WIDTH, HEIGHT);
        dataBuilder.withPoint(0, 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4);
        dataBuilder.withPoint(1, 0, (byte) 5, (byte) 6, (byte) 7, (byte) 8);
        dataBuilder.withPoint(0, 1, (byte) 9, (byte) 10, (byte) 11, (byte) 12);
        dataBuilder.withPoint(1, 1, (byte) 13, (byte) 14, (byte) 15, (byte) 16);
        //When
        ImageData imageData = dataBuilder.build();
        //Then
        assertThat(imageData.getData()).isSorted();
    }
}