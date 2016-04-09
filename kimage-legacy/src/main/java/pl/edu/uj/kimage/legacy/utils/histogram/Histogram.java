package pl.edu.uj.kimage.legacy.utils.histogram;

import pl.edu.uj.kimage.legacy.image.Image;

/**
 * @author Krzysztof
 */
public class Histogram {

    private final int[][] hist;
    private final boolean isOneChanel;
    private int nopoints;

    public Histogram(Image image, boolean grayscale) {
        isOneChanel = grayscale;
        final int width = image.getWidth();
        final int height = image.getHeight();

        if (isOneChanel) {
            hist = new int[1][256];
            for (int c = 0; c < width; c++) {
                for (int r = 0; r < height; r++) {
                    ++hist[0][image.getRed(c, r)];
                    ++nopoints;
                }
            }
        } else {
            hist = new int[3][256];
            for (int c = 0; c < width; c++) {
                for (int r = 0; r < height; r++) {
                    ++hist[0][image.getRed(c, r)];
                    ++hist[1][image.getGreen(c, r)];
                    ++hist[2][image.getBlue(c, r)];
                    ++nopoints;
                }
            }
        }
    }

    public int[] getRedHistogram() {
        return isOneChanel ? hist[0] : hist[0];
    }

    public int[] getGreenHistogram() {
        return isOneChanel ? hist[0] : hist[1];
    }

    public int[] getBlueHistogram() {
        return isOneChanel ? hist[0] : hist[2];
    }

    public int[][] getHistogram() {
        return hist;
    }

    public double getNoPoints() {
        return nopoints;
    }


}
