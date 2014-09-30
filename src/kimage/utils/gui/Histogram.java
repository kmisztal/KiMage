package kimage.utils.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kimage.image.Image;

/**
 *
 * @author Krzysztof
 */
public class Histogram extends JFrame {
    private final int[][] mapHistory;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Histogram(new Image("./res/apples.png"));
        });
    }

    public Histogram(Image image) {
        super("Histogram");
        LookAndFeel.doIt();
        // For this example, I just randomised some data, you would
        // Need to load it yourself...
        final int width = image.getWidth();
        final int height = image.getHeight();

        mapHistory = new int[3][256];
        for (int c = 0; c < width; c++) {
            for (int r = 0; r < height; r++) {
                ++mapHistory[0][image.getRed(c, r)];
                ++mapHistory[1][image.getGreen(c, r)];
                ++mapHistory[2][image.getBlue(c, r)];
            }
        }

        JPopupMenu popup = new JPopupMenu();
        // New project menu item
        JMenuItem menuItem = new JMenuItem("Save as ...");
        menuItem.addActionListener((ActionEvent e) -> {
            save();
        });
        popup.add(menuItem);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane sp = new JScrollPane();
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1, 0, 0));
        p.add(new Graph(mapHistory, 0));
        p.add(new Graph(mapHistory, 1));
        p.add(new Graph(mapHistory, 2));
        sp.setViewportView(p);

        sp.addMouseListener(new MouseAdapter() {

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

        add(sp);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected class Graph extends JPanel {

        protected static final int MIN_BAR_WIDTH = 4;
        private int[][] mapHistory;
        private final int pos;

        public Graph(int[][] mapHistory, int pos) {
            this.mapHistory = mapHistory;
            this.pos = pos;
            int width = (mapHistory[0].length * MIN_BAR_WIDTH) + 11;
            Dimension minSize = new Dimension(width, 128);
            Dimension prefSize = new Dimension(width, 256);
            setMinimumSize(minSize);
            setPreferredSize(prefSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mapHistory != null) {
                int xOffset = 5;
                int yOffset = 5;
                int width = getWidth() - 1 - (xOffset * 2);
                int height = getHeight() - 1 - (yOffset * 2);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(xOffset, yOffset, width, height);
                final int barWidth = Math.max(MIN_BAR_WIDTH,
                        (int) Math.floor((float) width
                                / (float) mapHistory[pos].length));
//                System.out.println("width = " + width + "; size = "
//                        + mapHistory[0].length + "; barWidth = " + barWidth);
                int maxValue = 0;
                for (int key : mapHistory[pos]) {
                    maxValue = Math.max(maxValue, key);
                }
                int xPos = xOffset;
                for (int key = 0; key < mapHistory[pos].length; ++key) {
                    final int value = mapHistory[pos][key];
                    final int barHeight = Math.round(((float) value
                            / (float) maxValue) * height);
                    switch (pos) {
                        case 0:
                            g2d.setColor(new Color(key, 0, 0));
                            break;
                        case 1:
                            g2d.setColor(new Color(0, key, 0));
                            break;
                        case 2:
                            g2d.setColor(new Color(0, 0, key));
                            break;
                    }

                    final int yPos = height + yOffset - barHeight;
                    Rectangle2D bar = new Rectangle2D.Float(
                            xPos, yPos, barWidth, barHeight);
                    g2d.fill(bar);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(bar);
                    xPos += barWidth;
                }
                g2d.dispose();
            }
        }
    }

    public final void save() {
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
            Container c = getContentPane();
            BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            c.paint(im.getGraphics());
            try {
                ImageIO.write(im, "png", fileToSave);
            } catch (IOException ex) {
                Logger.getLogger(ResizableImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public int[] getRedHistogram() {
        return mapHistory[0];
    }
    
    public int[] getGreenHistogram() {
        return mapHistory[1];
    }
    
    public int[] getBlueHistogram() {
        return mapHistory[2];
    }
    
}
