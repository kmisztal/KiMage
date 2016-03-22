package kimage.utils.gui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import kimage.image.Image;

/**
 * @author Krzysztof
 */
public final class ImageFrame extends JFrame {

    String core_title;
    FilenameFilter fileNameFilter;
    ResizableImagePanel imageView;

    public ImageFrame(String title) {
        super();
        LookAndFeel.doIt();
        EscapeClose.doIt(this);

        imageView = new ResizableImagePanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JScrollPane(imageView));
        setLocationByPlatform(true);
        pack();
        setSize(800, 600);
        setTitle(title == null ? "Step Handler Executor" : title);

        imageView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                ImageFrame.this.setTitle(ImageFrame.this.core_title + " ("
                        + v((me.getX() - imageView.getxPos()) / imageView.scale, imageView.originalImage.getWidth())
                        + ";"
                        + v((me.getY() - imageView.getyPos()) / imageView.scale, imageView.originalImage.getHeight()) + ")");
            }
        });

        // make the cursor a crosshair shape
        imageView.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

//        fileNameFilter = (File file, String name1) -> true;
        fileNameFilter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return true;
            }
        };

    }

    public ImageFrame(Image image) {
        this();
        imageView.setImage(image.getBufferedImage());
    }

    public ImageFrame(String title, Image image) {
        this(title);
        imageView.setImage(image.getBufferedImage());
    }

    private ImageFrame() {
        this("");
    }

    public static void display(Image img) {
        ImageFrame i = new ImageFrame(img);
        i.display();
    }

    public static void displayCopy(Image img) {
        display(img.copy());
    }

    private String v(double val, int max) {
        if (val < 0) {
            return "0";
        } else if (val > max) {
            return "" + max;
        } else {
            return "" + (int) val;
        }
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title); //To change body of generated methods, choose Tools | Templates.
        if (core_title == null) {
            core_title = title;
        }
    }

    //    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ImageFrame imageList1 = new ImageFrame();
//            imageList1.setVisible(true);
//        });
//    }
    public void display() {
        this.setVisible(true);
    }

}
