package pl.edu.uj.kimage.legacy.image;

import pl.edu.uj.kimage.legacy.helpers.ColorHelper;
import pl.edu.uj.kimage.legacy.helpers.IOHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <img src="doc-files/image.jpg" alt="None"> @author Krzysztof
 */
public class Image extends AbstractImage {
    static {
        type = BufferedImage.TYPE_INT_ARGB;
    }

    // Image
    protected BufferedImage image;

    protected Image() {
    }

    /**
     * default type is RGB
     *
     * @param width
     * @param height
     */
    public Image(final int width, final int height) {
        this(width, height, type);
    }

    /**
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
        width = img.getWidth();
        height = img.getHeight();
        //to make sure that we have a correct image type
        if (img.getType() != type) {
            final BufferedImage convertedImg = new BufferedImage(width, height, type);
            convertedImg.getGraphics().drawImage(img, 0, 0, null);
            this.image = convertedImg;
        } else {
            this.image = img;
        }
    }

    public Image(String filename) {
        this(IOHelper.load(filename));
    }

    public Image(String filename, int imageType) {
        this(IOHelper.load(filename, imageType));
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
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
     * @return
     */
    @Override
    public Image copy() {
        try {
            return this.getClass().getConstructor(BufferedImage.class).newInstance(copyImage(image));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getRGB(final int x, final int y) {
        return image.getRGB(x, y);
    }

    public void setRGB(int x, int y, int i) {
        image.setRGB(x, y, i);
    }

    public void setRGB(int x, int y, int r, int g, int b) {
        image.setRGB(x, y, new Color(r, g, b).getRGB());
    }

    public int getRed(final int x, final int y) {
        return ColorHelper.RED.getColor(image.getRGB(x, y));
    }

    public int getGreen(final int x, final int y) {
        return ColorHelper.GREEN.getColor(image.getRGB(x, y));
    }

    public int getBlue(final int x, final int y) {
        return ColorHelper.BLUE.getColor(image.getRGB(x, y));
    }

    public int getRGBSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return image.getRGB(x, y);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public void setRGBSecure(int x, int y, int i) {
        if (checkX(x) && checkY(y)) {
            image.setRGB(x, y, i);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getRedSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.RED.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getGreenSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.GREEN.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getBlueSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.BLUE.getColor(image.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public void setBufferedImage(BufferedImage im) {
        image = im;
        width = im.getWidth();
        height = im.getHeight();
    }

    public BufferedImage getCopyOfBufferedImage() {
        return copyImage(image);
    }

    public void save(String filemane) {
        IOHelper.save(filemane, getBufferedImage());
    }

}
