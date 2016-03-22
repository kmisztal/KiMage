package kimage.helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Krzysztof
 */
public class IOHelper {

    public static BufferedImage load(String filename) {
        try {
            BufferedImage ret = ImageIO.read(new File(filename));
            if (ret == null) {
                throw new RuntimeException("Wrong file format");
            }
            return ret;
        } catch (IOException | RuntimeException ex) {
            return null;
        }
    }

    public static BufferedImage load(String filename, int imageType) {
        try {
            return convert(ImageIO.read(new File(filename)), imageType);
        } catch (IOException ex) {
            throw new RuntimeException("Wrong local file name");
        }
    }

    private static BufferedImage convert(BufferedImage bi, int imageType) {
        final BufferedImage bufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), imageType);
        final Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawRenderedImage(bi, null);
        graphics.dispose();
        return bufferedImage;
    }

    public static void save(String filePath, BufferedImage image) {
        File file = new File(filePath);
        try {
            String filename = filePath.toUpperCase();
            if (filename.endsWith(".JPEG") || filename.endsWith(".JPG")) {
                ImageIO.write(image, "JPEG", file);
            } else if (filename.endsWith(".PNG")) {
                ImageIO.write(image, "PNG", file);
            } else {
                ImageIO.write(image, filePath.substring(filePath.lastIndexOf('.') + 1), file);
            }
        } catch (Exception e) {
        }
    }
}
