package pl.edu.uj.kimage.awt;

import pl.edu.uj.kimage.plugin.model.Image;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageConvolution {

    private static final ImageConverter imageConverter = new ImageConverter();

    public static Image convolve(Image inputImage, float[] kernel, int kernelWidth, int kernelHeight){
        BufferedImage inputBufferedImage = imageConverter.toBufferedImage(inputImage);
        BufferedImage outputBufferedImage = new BufferedImage(inputImage.getWidth(),
                inputImage.getHeight(), inputBufferedImage.getType());

        final BufferedImageOp bio = new ConvolveOp(new Kernel(kernelWidth, kernelHeight, kernel));

        try {
            bio.filter(inputBufferedImage, outputBufferedImage);
        } catch (java.lang.IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        return imageConverter.toImage(outputBufferedImage);
    }

}
