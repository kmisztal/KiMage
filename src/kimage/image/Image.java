package kimage.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import kimage.image.color.MColor;
import kimage.utils.IOHelper;

/**
 * <img src="doc-files/image.jpg">
 * @author Krzysztof
 */
public class Image extends AbstractImage {

    // Image
    protected BufferedImage image;

    /**
     * default type is RGB
     *
     * @param width
     * @param height
     */
    public Image(final int width, final int height) {
        this(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     *
     * @param width
     * @param height
     * @param imageType - type of image from BufferedImage
     */
    public Image(final int width, final int height, final int imageType) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, imageType);
    }

    /**
     * Constructor using a image in memory
     *
     * @param img Image
     */
    public Image(BufferedImage img) {
        this.image = img;
        width = img.getWidth();
        height = img.getHeight();
    }

    public Image(String filename) {
        this(IOHelper.load(filename));
    }

    public void fillWithColor(Color c) {
        Graphics2D g = image.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, width, height);
        g.dispose();
    }

    public void clearImage(Color c) {
        fillWithColor(c);
    }

    /**
     * Gets the type
     */
    public int getType() {
        return image.getType();
    }

    /**
     *
     * @return
     */
    @Override
    public Image copy() {
        return new Image(copyImage(image));
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public final int getRGB(final int x, final int y) {
        return image.getRGB(x, y);
    }
    
    public void setRGB(int x, int y, int i) {
        image.setRGB(x, y, i);
    }
    
    public void setRGB(int x, int y, int r, int g, int b) {
        image.setRGB(x, y, new Color(r,g,b).getRGB());
    }

    public final int getRed(final int x, final int y) {
        return MColor.RED.getColor(image.getRGB(x, y));
    }

    public final int getGreen(final int x, final int y) {
        return MColor.GREEN.getColor(image.getRGB(x, y));
    }

    public final int getBlue(final int x, final int y) {
        return MColor.BLUE.getColor(image.getRGB(x, y));
    }

    public final int getRGBSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return image.getRGB(x, y);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }
    
    public void setRGBSecure(int x, int y, int i) {
        if (checkX(x) && checkY(y)) {
            image.setRGB(x, y, i);
        }else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public final int getRedSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return MColor.RED.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public final int getGreenSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return MColor.GREEN.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public final int getBlueSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return MColor.BLUE.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public BufferedImage getCopyOfBufferedImage() {
        return copyImage(image);
    }
    
    public void save(String filemane){
        IOHelper.save(filemane, getBufferedImage());
    }

    public void setBufferedImage(BufferedImage im) {
        image = im;
        width = im.getWidth();
        height = im.getHeight();
    }


}
