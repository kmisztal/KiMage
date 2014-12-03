package kimage.utils;

import java.awt.Color;
import kimage.image.Image;

/**
 *
 * @author Krzysztof
 */
public class Mask {

    private final boolean mask[][];

    public Mask(Image img, Color trueColor) {
        mask = new boolean[img.getWidth()][img.getHeight()];
        final int trueColor_ = trueColor.getRGB();
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                mask[x][y] = img.getRGB(x, y) == trueColor_;
            }
        }
    }

    public boolean get(int x, int y) {
        return mask[x][y];
    }

    public Image getImage() {
        Image ret = new Image(mask.length, mask[0].length);
        for (int x = 0; x < ret.getWidth(); ++x) {
            for (int y = 0; y < ret.getHeight(); ++y) {
                ret.setRGB(x, y, mask[x][y] ? Color.red.getRGB() : Color.white.getRGB());
            }
        }
        
        return ret;
    }

}
