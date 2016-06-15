package pl.edu.uj.kimage.awt;

import java.awt.image.BufferedImage;
import java.util.Random;
import org.junit.Test;
import pl.edu.uj.kimage.plugin.model.Image;

import static org.assertj.core.api.Assertions.assertThat;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;


public class ImageConverterTest {

    @Test
    public void converterFromBufferedImageToImageReturnsValidValues() {
        // given
        int imageWidth = 4;
        int imageHeight = 4;
        ImageConverter converter = new ImageConverter();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Random generator = new Random();

        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                image.setRGB(i, j, generator.nextInt(100));
            }
        }
        // when
        Image output = converter.toImage(image);

        // then
        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                int argb = image.getRGB(i, j);

                assertThat((argb >> 16) & 0x000000FF).isEqualTo(output.getColor(i, j).getRed());
                assertThat((argb >> 8) & 0x000000FF).isEqualTo(output.getColor(i, j).getGreen());
                assertThat((argb) & 0x000000FF).isEqualTo(output.getColor(i, j).getBlue());
                assertThat((argb >> 24) & 0x000000FF).isEqualTo(output.getColor(i, j).getAlpha());
            }
        }
    }

    @Test
    public void converterFromImageToBufferedImageReturnsValidValues(){
        // given
        int imageWidth = 4;
        int imageHeight = 4;
        ImageConverter converter = new ImageConverter();
        ImageBuilder imageBuilder = new ImageBuilder(imageWidth,imageHeight);
        Random generator = new Random();

        for(int i = 0; i < imageWidth; i++){
            for(int j = 0; j < imageHeight; j++){
                imageBuilder.withColor(new Color(generator.nextInt(255),generator.nextInt(255),generator.nextInt(255),generator.nextInt(255)), i, j);
            }
        }

        Image image = imageBuilder.build();

        // when
        BufferedImage output = converter.toBufferedImage(image);

        // then
        for(int i = 0; i < imageWidth; i++){
            for(int j =0; j < imageHeight; j++){
                int argb = output.getRGB(i, j);

                assertThat((argb >> 16) & 0x000000FF).isEqualTo(image.getColor(i, j).getRed());
                assertThat((argb >> 8) & 0x000000FF).isEqualTo(image.getColor(i, j).getGreen());
                assertThat((argb) & 0x000000FF).isEqualTo(image.getColor(i, j).getBlue());
                assertThat((argb >> 24) & 0x000000FF).isEqualTo(image.getColor(i, j).getAlpha());
            }
        }
    }

}
