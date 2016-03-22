package kimage.utils.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Krzysztof
 */
public class ResizableImagePanel extends JPanel {

    protected String m_ImageFile;             // the path and name of the image file to be displayed
    protected BufferedImage m_Image;          // instance of the image to be displayed
    protected BufferedImage m_ScaledImage;    // scaled instance of the image to be displayed
    protected BufferedImage originalImage;    // original image from file
    protected double scale;
    protected boolean empty = true;
    boolean is_scaled = true;
    private double xPos;
    private double yPos;

    /**
     * Creates a new instance of ImagePanel
     *
     * @param imageFile
     */
    public ResizableImagePanel(String imageFile) {
        if (imageFile == null) {
            this.m_ImageFile = null;
            this.empty = true;
        } else {
            this.empty = false;
            this.m_ImageFile = imageFile;
        }
        initComponents();
    }

    /**
     * Creates a new instance of ImagePanel
     */
    public ResizableImagePanel() {
        this.m_ImageFile = "";

        final JPopupMenu popup = new JPopupMenu();
        // New project menu item
        JMenuItem menuItem = new JMenuItem("Save as ...");
//        menuItem.addActionListener((ActionEvent e) -> {
//            save();
//        });

        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        popup.add(menuItem);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * image deep copy
     *
     * @param source
     * @return
     */
    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    /**
     * Initialise the class components
     */
    protected final void initComponents() {
        // try to load the image file
        if (!empty) {
            try {
                m_Image = ImageIO.read(new File(m_ImageFile));
                originalImage = copyImage(m_Image);
            } catch (IOException ex) {
            }
        }
    }

    public void setImage(String im) {
        this.m_ImageFile = im;
        try {
            m_Image = ImageIO.read(new File(m_ImageFile));
            originalImage = copyImage(m_Image);
        } catch (IOException ex) {
        }
        empty = false;
        repaint();

    }

    /**
     * Calculate the scale required to correctly fit the image into panel
     *
     * @param panelWidth  - width of the panel
     * @param panelHeight - height of the panel
     * @param imageWidth  - image width
     * @param imageHeight - image height
     * @return - scale for image
     */
    protected double getScale(final int panelWidth, final int panelHeight, final int imageWidth, final int imageHeight) {
        final double xScale = (double) panelWidth / imageWidth;
        final double yScale = (double) panelHeight / imageHeight;
        return Math.min(xScale, yScale);
    }

    /**
     * Override paint method of the panel
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (m_Image != null) {
            super.paintComponent(g);

            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            m_ScaledImage = m_Image;

            // Get the required sizes for display and calculations
            final int panelWidth = this.getWidth();
            final int panelHeight = this.getHeight();
            final int imageWidth = m_ScaledImage.getWidth();
            final int imageHeight = m_ScaledImage.getHeight();
            if (is_scaled) {
                scale = getScale(panelWidth, panelHeight, imageWidth, imageHeight);
            } else {
                scale = 1.;
            }
            // Calculate the center position of the panel -- with scale
            xPos = (panelWidth - scale * imageWidth) / 2;
            yPos = (panelHeight - scale * imageHeight) / 2;

            // Locate, scale and draw image
            final AffineTransform at = AffineTransform.getTranslateInstance(xPos, yPos);
            at.scale(scale, scale);
            g2.drawRenderedImage(m_ScaledImage, at);
        }
    }

    /**
     * reset panel to original image
     */
    public void reset() {
        m_Image = copyImage(originalImage);
        repaint();
    }

    public BufferedImage getImage() {
        return copyImage(originalImage);
    }

    public void setImage(BufferedImage im) {
        if (im != null) {
            m_Image = copyImage(im);
            originalImage = copyImage(im);
            repaint();
        }
    }

    public void scaled(boolean v) {
        is_scaled = v;
    }

    public final void save() {
        if (m_Image == null) {
            JOptionPane.showMessageDialog(this, "Please open some image");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter(
                "PNG", ImageIO.getReaderFileSuffixes());
        //Attaching Filter to JFileChooser object
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            String extension = "";
            String fileName = fileToSave.getAbsolutePath();
            int i = fileName.lastIndexOf('.');
            if (i >= 0) {
                extension = fileName.substring(i + 1).toLowerCase();
            }

            if (!"png".equals(extension)) {
                fileToSave = new File(fileName + ".png");
            }

            try {
                ImageIO.write(m_Image, "png", fileToSave);
            } catch (IOException ex) {
                Logger.getLogger(ResizableImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public double getScale() {
        return scale;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }


}
