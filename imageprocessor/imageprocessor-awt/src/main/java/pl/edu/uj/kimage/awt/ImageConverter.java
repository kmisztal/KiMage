package pl.edu.uj.kimage.awt;

import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.awt.image.BufferedImage;


public class ImageConverter {

    public Image toImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        ImageBuilder imageBuilder = new ImageBuilder(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int argb = image.getRGB(i, j);
                imageBuilder.withColor(new Color((argb >> 16) & 0x000000FF, (argb >> 8) & 0x000000FF, (argb) & 0x000000FF, (argb >> 24) & 0xff), i, j);
            }
        }

        return imageBuilder.build();
    }

    public BufferedImage toBufferedImage(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color argb = image.getColor(i, j);
                bufferedImage.setRGB(i, j, getIntFromColor(argb.getAlpha(), argb.getRed(), argb.getGreen(), argb.getBlue()));
            }
        }

        return bufferedImage;
    }


    private int getIntFromColor(int alpha, int red, int green, int blue) {
        red = (red << 16);
        green = (green << 8);
        alpha = (alpha << 24);

        return alpha | red | green | blue;
    }

}
