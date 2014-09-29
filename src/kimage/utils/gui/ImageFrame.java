package kimage.utils.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import javafx.util.Pair;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import kimage.image.Image;

/**
 *
 * @author Krzysztof
 */
public class ImageFrame extends JFrame {

    FilenameFilter fileNameFilter;
    ResizableImagePanel imageView;

    public ImageFrame(String title) {
        super(title == null ? "Step Handler Executor" : title);
        LookAndFeel.doIt();


        imageView = new ResizableImagePanel();

        fileNameFilter = (File file, String name1) -> true;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JScrollPane(imageView));
        setLocationByPlatform(true);
        pack();
        setSize(800, 600);
    }

    public ImageFrame(Image image)  {
        this();
        imageView.setImage(image.getBufferedImage());
    }
    
    private ImageFrame() {
        this("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageFrame imageList1 = new ImageFrame();
            imageList1.setVisible(true);
        });
    }

    public void display() {
        this.setVisible(true);
    }

}
