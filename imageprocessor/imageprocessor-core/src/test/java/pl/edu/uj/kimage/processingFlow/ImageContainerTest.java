package pl.edu.uj.kimage.processingFlow;

import org.junit.Test;
import pl.edu.uj.kimage.Image;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageContainerTest {

    @Test
    public void createSampleImageObject() {
        Image image = new Image(512, 256);
        assertThat(image.getWidth()).isEqualTo(512);
        assertThat(image.getHeight()).isEqualTo(256);
    }

    @Test
    public void oneChannelOperation() {
        Image image = new Image(256, 256);
        int red = 128;
        image.setRed(5, 5, red);
        assertThat(image.getRed(5, 5)).isEqualTo(red);
    }

    @Test
    public void multiChannelOperation() {
        Image image = new Image(256, 256);

        int x = 0, y = 0;
        int red = 0, green = 128, blue = 200;

        image.setRGB(x, y, red, green, blue);

        int[] colors = image.getRGB(x, y);

        assertThat(colors[0]).isEqualTo(red);
        assertThat(colors[1]).isEqualTo(green);
        assertThat(colors[2]).isEqualTo(blue);
    }

    @Test
    public void colorValuesClamping() {
        Image image = new Image(256, 256);

        int x = 0, y = 0;
        int red = 0, green = -5, blue = 300;

        image.setRGB(0, 0, red, green, blue);

        int[] colors = image.getRGB(x, y);

        assertThat(colors[0]).isEqualTo(0);
        assertThat(colors[1]).isEqualTo(0);
        assertThat(colors[2]).isEqualTo(255);
    }
}
