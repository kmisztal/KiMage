package pl.edu.uj.kimage.image;

import pl.edu.uj.kimage.helpers.ColorHelper;
import pl.edu.uj.kimage.helpers.IOHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class BIProxy {

    int fromY, toY, fromY_clear, toY_clear;
    boolean last;
    int[] image;
    int width;
    int height;

    public BIProxy(BufferedImage img, int fromY, int toY, int offset, boolean last) {
        this.image = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        this.width = img.getWidth();
        this.height = img.getHeight();

        this.fromY_clear = fromY;
        this.toY_clear = toY;

        if (this.last) {
            fromY -= offset;
            this.fromY = (fromY < 0) ? 0 : fromY;
            this.toY = (toY > img.getHeight()) ? img.getHeight() : toY;
        } else {
            toY += offset;
            this.fromY = (fromY < 0) ? 0 : fromY;
            this.toY = (toY > img.getHeight()) ? img.getHeight() : toY;
        }
    }

    public int getRGB(int x, int y) {
        return image[x + (y + fromY) * width];
    }

    public void setRGB(int x, int y, int i) {
        if ((this.last && y < this.fromY_clear) || (!this.last && y < this.toY_clear)) {
            return;
        }
        image[x + (y + fromY) * width] = i;
    }
}

/**
 * @author Krzysztof
 */
public class ImageForThreads extends Image {

    BIProxy bimage;
    int fromY;
    int toY;

    public ImageForThreads(Image img, int fromY, int toY, int offset, boolean last) {
        width = img.getWidth();
        height = toY - fromY + offset;
        this.fromY = fromY;
        this.toY = toY;

        //to make sure that we have a correct image type
        this.bimage = new BIProxy(img.getBufferedImage(), fromY, toY, offset, last);
        this.image = img.image;

    }

    @Override
    public void fillWithColor(Color c) {
        throw new RuntimeException("You are CRAZY !!!");
    }

    public void clearImage(Color c) {
        fillWithColor(c);
    }

    /**
     * @return
     */
    @Override
    public Image copy() {
        throw new RuntimeException("You are CRAZY !!!");
    }

    @Override
    public int getRGB(final int x, final int y) {
        return bimage.getRGB(x, y);
    }

    @Override
    public void setRGB(int x, int y, int i) {
        bimage.setRGB(x, y, i);
    }

    @Override
    public void setRGB(int x, int y, int r, int g, int b) {
        bimage.setRGB(x, y, new Color(r, g, b).getRGB());
    }

    @Override
    public int getRed(final int x, final int y) {
        return ColorHelper.RED.getColor(bimage.getRGB(x, y));
    }

    @Override
    public int getGreen(final int x, final int y) {
        return ColorHelper.GREEN.getColor(bimage.getRGB(x, y));
    }

    @Override
    public int getBlue(final int x, final int y) {
        return ColorHelper.BLUE.getColor(bimage.getRGB(x, y));
    }

    @Override
    public int getRGBSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return bimage.getRGB(x, y);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    @Override
    public void setRGBSecure(int x, int y, int i) {
        if (checkX(x) && checkY(y)) {
            bimage.setRGB(x, y, i);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    @Override
    public int getRedSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.RED.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    @Override
    public int getGreenSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.GREEN.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    @Override
    public int getBlueSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.BLUE.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        throw new RuntimeException("You are CRAZY !!!");
    }

    @Override
    public void setBufferedImage(BufferedImage im) {
        throw new RuntimeException("You are CRAZY !!!");
    }

    @Override
    public BufferedImage getCopyOfBufferedImage() {
        throw new RuntimeException("You are CRAZY !!!");
    }

    @Override
    public void save(String filemane) {
        IOHelper.save(filemane, getBufferedImage());
    }

    public synchronized void finalize_work() {
        for (int y = fromY; y < toY; ++y) {
            for (int x = 0; x < width; ++x) {
                this.image.setRGB(x, y, bimage.image[y * width + x]);
            }
        }
    }

}
