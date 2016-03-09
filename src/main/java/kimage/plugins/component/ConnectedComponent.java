package kimage.plugins.component;

import kimage.helpers.ColorTable;
import kimage.image.Image;
import kimage.plugin.Plugin;

import java.awt.*;

/**
 * @author Krzysztof
 */
public class ConnectedComponent extends Plugin {

    final int MAX_LABELS = 80000;
    int next_label = 1;
    private int[][] res;

    public static int[][] toIntArray(Image img, int signal, int background) {
        int[][] ret = new int[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                ret[i][j] = img.getRed(i, j) < 128 ? signal : background;
            }
        }
        return ret;
    }

    public static int[][] toIntArray(Image img) {
        return toIntArray(img, 1, 0);
    }

    public static void fromIntArray(int[][] im, int size, Image out) {
        Color[] c = ColorTable.randomColorArray(size);
        c[0] = Color.white;
        for (int i = 0; i < out.getWidth(); i++) {
            for (int j = 0; j < out.getHeight(); j++) {
                out.setRGB(i, j, c[im[i][j]].getRGB());
            }
        }
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        res = compactLabeling(toIntArray(imgIn), imgIn.getWidth(), imgIn.getHeight(), true);
        final int scale = getMaxLabel() + 1;
        fromIntArray(res, scale, imgOut);
        setAttribute("result", res);
        setAttribute("nr", getMaxLabel());
    }

    /**
     * label and re-arrange the labels to make the numbers of label continous
     *
     * @param image
     * @param w
     * @param h
     * @param zeroAsBg Leaving label 0 untouched
     * @return
     */
    public int[][] compactLabeling(int[][] image, int w, int h, boolean zeroAsBg) {
        //label first
        int[][] label = labeling(image, w, h, zeroAsBg);
        int[] stat = new int[next_label + 1];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (label[i][j] > next_label) {
                    System.err.println("bigger label than next_label found!");
                }
                stat[label[i][j]]++;
            }
        }

        stat[0] = 0;              // label 0 will be mapped to 0
        // whether 0 is background or not
        int k = 1;
        for (int i = 1; i < stat.length; i++) {
            if (stat[i] != 0) {
                stat[i] = k++;
            }
        }

//        System.out.println("From " + next_label + " to " + (k - 1) + " regions");
        next_label = k - 1;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                label[i][j] = stat[label[i][j]];
            }
        }
        return label;
    }

    /**
     * return the max label in the labeling process. the range of labels is
     * [0..max_label]
     *
     * @return
     */
    public int getMaxLabel() {
        return next_label;
    }

    /**
     * Label the connect components If label 0 is background, then label 0 is
     * untouched; If not, label 0 may be reassigned [Requires] 0 is treated as
     * background
     *
     * @param image    data
     * @param w
     * @param h
     * @param zeroAsBg label 0 is treated as background, so be ignored
     * @return
     */
    public int[][] labeling(int[][] image, int w, int h, boolean zeroAsBg) {
        int[][] rst = new int[w][h];
        int[] parent = new int[MAX_LABELS];
        int[] labels = new int[MAX_LABELS];
        // region label starts from 1;
        // this is required as union-find data structure
        int next_region = 1;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                if (image[x][y] == 0 && zeroAsBg) {
                    continue;
                }
                int k = 0;
                boolean connected = false;
                // if connected to the left
                if (x > 0 && image[x - 1][y] == image[x][y]) {
                    k = rst[x - 1][y];
                    connected = true;
                }
                // if connected to the up
                if (y > 0 && image[x][y - 1] == image[x][y]
                        && (connected = false || image[x][y - 1] < k)) {
                    k = rst[x][y - 1];
                    connected = true;
                }
                if (!connected) {
                    k = next_region;
                    next_region++;
                }

                if (k >= MAX_LABELS) {
                    System.err.println("maximum number of labels reached. "
                            + "increase MAX_LABELS and recompile.");
                    System.exit(1);
                }
                rst[x][y] = k;
                // if connected, but with different label, then do union
                if (x > 0 && image[x - 1][y] == image[x][y] && rst[x - 1][y] != k) {
                    uf_union(k, rst[x - 1][y], parent);
                }
                if (y > 0 && image[x][y - 1] == image[x][y] && rst[x][y - 1] != k) {
                    uf_union(k, rst[x][y - 1], parent);
                }
            }
        }

        // Begin the second pass.  Assign the new labels
        // if 0 is reserved for background, then the first available label is 1
        next_label = 1;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (image[i][j] != 0 || !zeroAsBg) {
                    rst[i][j] = uf_find(rst[i][j], parent, labels);
                    // The labels are from 1, if label 0 should be considered, then
                    // all the label should minus 1
                    if (!zeroAsBg) {
                        rst[i][j]--;
                    }
                }
            }
        }
        next_label--;   // next_label records the max label
        if (!zeroAsBg) {
            next_label--;
        }

//        System.out.println(next_label + " regions");
        return rst;
    }

    void uf_union(int x, int y, int[] parent) {
        while (parent[x] > 0) {
            x = parent[x];
        }
        while (parent[y] > 0) {
            y = parent[y];
        }
        if (x != y) {
            if (x < y) {
                parent[x] = y;
            } else {
                parent[y] = x;
            }
        }
    }

    /**
     * This function is called to return the root label Returned label starts
     * from 1 because label array is inited to 0 as first [Effects] label array
     * records the new label for every root
     */
    int uf_find(int x, int[] parent, int[] label) {
        while (parent[x] > 0) {
            x = parent[x];
        }
        if (label[x] == 0) {
            label[x] = next_label++;
        }
        return label[x];
    }
}
