package pl.edu.uj.kimage.awt;

import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.awt.image.BufferedImage;


public class ImageConverter {

    private ImageConverter() { }

    public static Image toImage(final BufferedImage image) {

        final int width = image.getWidth();
        final int height = image.getHeight();

        final ImageBuilder imageBuilder = new ImageBuilder(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final int argb = image.getRGB(i, j);
                imageBuilder.withColor(new Color((argb >> 16) & 0x000000FF, (argb >> 8) & 0x000000FF, (argb) & 0x000000FF, (argb >> 24) & 0xff), i, j);
            }
        }

        return imageBuilder.build();
    }

    public static BufferedImage toBufferedImage(final Image image) {

        final int width = image.getWidth();
        final int height = image.getHeight();

        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                final Color argb = image.getColor(i, j);
                bufferedImage.setRGB(i, j, getIntFromColor(argb.getAlpha(), argb.getRed(), argb.getGreen(), argb.getBlue()));
            }
        }

        return bufferedImage;
    }


    private static int getIntFromColor(int alpha, int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        alpha = (alpha << 24) & 0xFF000000;

        return alpha | red | green | blue;
    }

}
