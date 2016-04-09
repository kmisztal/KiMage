package pl.edu.uj.kimage.legacy.plugin.linear;

import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.plugin.Plugin;

import java.awt.image.BufferedImage;

/**
 * @author Krzysztof
 */
public abstract class LinearPlugin extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        checkAttributes();
        linearProcess(imgIn, imgOut);
    }

    protected abstract int colorChange(int rgb);

    private void linearProcess(Image imgIn, Image imgOut) {
        if (imgOut.getBufferedImage().getType() != BufferedImage.TYPE_INT_ARGB) {
            throw new RuntimeException("Wrong output image color space");
        }

        int[] rgbArray = new int[imgIn.getWidth() * imgIn.getHeight()];
        rgbArray = imgIn.getBufferedImage().getRGB(0, 0, imgIn.getWidth(), imgIn.getHeight(), rgbArray, 0, imgIn.getWidth());
        for (int i = 0, q = rgbArray.length; i < q; i++) {
            rgbArray[i] = colorChange(rgbArray[i]);
        }
        imgOut.getBufferedImage().setRGB(0, 0, imgOut.getWidth(), imgOut.getHeight(), rgbArray, 0, imgOut.getWidth());
    }

    /**
     * setup needed attributes
     */
    protected void checkAttributes() {
    }
}
