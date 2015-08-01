package kimage.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kimage.helpers.ColorHelper;
import kimage.helpers.IOHelper;
import kimage.utils.gui.ImageFrame;

class BIProxy{
    int fromY;    
    BufferedImage image;
    
    
    public BIProxy(BufferedImage image, int fromY) {
        this.image = image;
        this.fromY = fromY;
    }

    public int getRGB(int x, int y) {
        return image.getRGB(x, y + fromY);
    }

    public void setRGB(int x, int y, int i) {
        image.setRGB(x, y+fromY, i);
    }

    Graphics2D createGraphics() {
        return image.createGraphics();
    }

    BufferedImage getSubimage(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }
    
}

/**
 *
 * @author Krzysztof
 */
public class ImageForThreads extends Image {

    int fromY;
    int toY;
    int offset;
    boolean last = false;
    BIProxy bimage;
    
    
    public ImageForThreads(Image img, int fromY, int toY, int offset, boolean last) {
        width = img.getWidth();
        height = toY - fromY + offset;

        if (last) {
            fromY -= offset;
            this.fromY = (fromY < 0) ? 0 : fromY;
            this.toY = (toY > img.height) ? img.height : toY;
        } else {
            toY += offset;
            this.fromY = (fromY < 0) ? 0 : fromY;
            this.toY = (toY > img.height) ? img.height : toY;
        }

        //to make sure that we have a correct image type
        this.bimage = new BIProxy(img.getBufferedImage(), this.fromY);
        this.image = this.bimage.image;
        
//        System.out.println("("+width+";"+height+"|" + fromY + ")");
//        new ImageFrame(new Image(this.image)).display();
    }

    @Override
    public void fillWithColor(Color c) {
        Graphics2D g = bimage.createGraphics();
        g.setColor(c);
        g.fillRect(0, fromY, width, height);
        g.dispose();
    }

    public void clearImage(Color c) {
        fillWithColor(c);
    }

    /**
     *
     * @return
     */
    @Override
    public Image copy() {
        try {
            return this.getClass().getConstructor(BufferedImage.class).newInstance(copyImage(bimage.getSubimage(0, fromY, width, height)));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public int getBlue(final int x, final int y) {
        return ColorHelper.BLUE.getColor(bimage.getRGB(x, y));
    }

    public int getRGBSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return bimage.getRGB(x, y);
        } else {
            throw new RuntimeException("Position outside the bimage: [" + x + ";" + y + "]");
        }
    }

    public void setRGBSecure(int x, int y, int i) {
        if (checkX(x) && checkY(y)) {
            bimage.setRGB(x, y, i);
        } else {
            throw new RuntimeException("Position outside the bimage: [" + x + ";" + y + "]");
        }
    }

    public int getRedSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.RED.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the bimage: [" + x + ";" + y + "]");
        }
    }

    public int getGreenSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.GREEN.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the bimage: [" + x + ";" + y + "]");
        }
    }

    public int getBlueSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.BLUE.getColor(bimage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the bimage: [" + x + ";" + y + "]");
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bimage.getSubimage(0, fromY, width, height);
    }

    @Override
    public BufferedImage getCopyOfBufferedImage() {
        return copyImage(bimage.getSubimage(0, fromY, width, height));
    }

    @Override
    public void save(String filemane) {
        IOHelper.save(filemane, getBufferedImage());
    }

    @Override
    public void setBufferedImage(BufferedImage im) {
        throw new RuntimeException("You are CRAZY !!!");
    }

}
