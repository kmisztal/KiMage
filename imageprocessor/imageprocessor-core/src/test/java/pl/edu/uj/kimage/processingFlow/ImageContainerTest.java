package pl.edu.uj.kimage.processingFlow;

import org.junit.Test;
import pl.edu.uj.kimage.Color;
import pl.edu.uj.kimage.Image;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageContainerTest {

    @Test
    public void sizeAccessorsReturnValidValues() {
        int imageWidth = 2, imageHeight = 2;
        Color red = new Color(255, 0, 0);
        Color green = new Color(0, 255, 0);
        Color blue = new Color(0, 0, 255);
        Color[] colors = new Color[]{red, green, blue, red};

        Image image = new Image(imageWidth, imageHeight, colors);
        assertThat(image.getWidth()).isEqualTo(imageWidth);
        assertThat(image.getHeight()).isEqualTo(imageHeight);
    }

    @Test
    public void imageColorAccessorReturnValidValues() {
        int imageWidth = 1, imageHeight = 1;
        int xPixelPos = 0, yPixelPos = 0;
        Color redColor = new Color(255, 0, 0);
        Color[] colors = new Color[]{new Color(redColor.getRed(), redColor.getGreen(), redColor.getBlue())};

        Image image = new Image(imageWidth, imageHeight, colors);
        Color returnedColor = image.getColor(xPixelPos, yPixelPos);

        assertThat(returnedColor.getRed()).isEqualTo(redColor.getRed());
        assertThat(returnedColor.getGreen()).isEqualTo(redColor.getGreen());
        assertThat(returnedColor.getBlue()).isEqualTo(redColor.getBlue());
    }

    @Test
    public void imageColorAccessorReturnClampedValues() {
        int imageWidth = 1, imageHeight = 1;
        int redChannel = 0, greenChannel = -25, blueChannel = 280;
        int xPixelPos = 0, yPixelPos = 0;
        Color blue = new Color(0, 0, 255);
        Color[] colors = new Color[]{new Color(redChannel, greenChannel, blueChannel)};

        Image image = new Image(imageWidth, imageHeight, colors);
        Color returnedColor = image.getColor(xPixelPos, yPixelPos);

        assertThat(returnedColor.getRed()).isEqualTo(blue.getRed());
        assertThat(returnedColor.getGreen()).isEqualTo(blue.getGreen());
        assertThat(returnedColor.getBlue()).isEqualTo(blue.getBlue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void imageSizeDoesNotMatchInputSize() {
        int imageWidth = 1, imageHeight = 1;
        Color red = new Color(255, 0, 0);
        Color[] colors = new Color[]{red, red, red, red};

        Image image = new Image(imageWidth, imageHeight, colors);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongImagePixelCoordinates() {
        int imageWidth = 2, imageHeight = 2;
        int xPixelPos = 2, yPixelPos = 1;
        Color red = new Color(255, 0, 0);
        Color[] colors = new Color[]{red, red, red, red};

        Image image = new Image(imageWidth, imageHeight, colors);
        Color returnedColor = image.getColor(xPixelPos, yPixelPos);
    }
}
