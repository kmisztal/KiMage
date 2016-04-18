package pl.edu.uj.kimage.processingFlow;

import org.junit.Test;
import pl.edu.uj.kimage.Color;
import pl.edu.uj.kimage.Image;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageContainerTest {

    @Test
    public void sizeAccessorsReturnValidValues() {
        int width = 2, height = 2;
        Color[] colors = new Color[]{new Color(255, 0, 0), new Color(0, 255, 0),
                                     new Color(0, 0, 255), new Color(255, 0, 0)};

        Image image = new Image(width, height, colors);
        assertThat(image.getWidth()).isEqualTo(width);
        assertThat(image.getHeight()).isEqualTo(height);
    }

    @Test
    public void channelColorAccessorsReturnValidValues() {
        int width = 1, height = 1;
        int redChannel = 255, greenChannel = 0, blueChannel = 0;
        Color[] colors = new Color[]{new Color(redChannel, greenChannel, blueChannel)};

        Image image = new Image(width, height, colors);
        assertThat(image.getRed(0, 0)).isEqualTo(redChannel);
        assertThat(image.getGreen(0, 0)).isEqualTo(greenChannel);
        assertThat(image.getBlue(0, 0)).isEqualTo(blueChannel);
    }

    @Test
    public void channelColorAccessorsReturnClampedValues() {
        int width = 1, height = 1;
        int redChannel = 0, greenChannel = -25, blueChannel = 280;
        Color[] colors = new Color[]{new Color(redChannel, greenChannel, blueChannel)};

        Image image = new Image(width, height, colors);
        assertThat(image.getRed(0, 0)).isEqualTo(0);
        assertThat(image.getGreen(0, 0)).isEqualTo(0);
        assertThat(image.getBlue(0, 0)).isEqualTo(255);
    }

    @Test
    public void fullColorAccessorsReturnValidData() {
        int width = 2, height = 2;
        Color[] colors = new Color[]{new Color(255, 0, 0), new Color(0, 255, 0),
                                     new Color(0, 0, 255), new Color(255, 0, 255)};

        Image image = new Image(width, height, colors);
        Color rgba = image.getRGBA(1, 1);
        assertThat(rgba.getRed()).isEqualTo(255);
        assertThat(rgba.getGreen()).isEqualTo(0);
        assertThat(rgba.getBlue()).isEqualTo(255);
        assertThat(rgba.getAlpha()).isEqualTo(255);
    }
}
