package kimage.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Krzysztof
 */
public class BinaryImage extends Image {


    static {
        type = BufferedImage.TYPE_BYTE_BINARY;
    }

    public BinaryImage(String filename) {
        super(filename, BufferedImage.TYPE_BYTE_BINARY);
    }

    public BinaryImage(int width, int height) {
        super(width, height);
    }

    /**
     * Constructor using a image in memory
     *
     * @param img Image
     */
    public BinaryImage(BufferedImage img) {
        super(img);
    }

//    /**
//     *
//     * @return
//     */
//    @Override
//    public BinaryImage copy() {
//        return new BinaryImage(copyImage(image));
//    }

    public static BinaryImage getImage(String filename) {
        return new BinaryImage(filename);
    }

    public ArrayList<Point> getForeground() {
        ArrayList<Point> ret = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (image.getRGB(i, j) != -1) {
                    ret.add(new Point(i, j));
                }
            }
        }
        return ret;
    }

}
