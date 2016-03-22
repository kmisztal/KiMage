package kimage.plugin.measurement;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 * @author Marcin Chołoniewski
 */
public abstract class ShapeMeasurement extends Plugin {

    private static final double SQRT_2 = Math.sqrt(2);
    private static final double C = (1 + SQRT_2) / 2;

    private double[][] raw_moments;
    private double[][] central_moments;
    private int width, height;

    private Boolean isEdge(Image img, int i, int j) {
        if (img.getRed(i, j) != 0) {
            return false;
        }

        int neighbors = 0;
        if (i > 0 && img.getRed(i - 1, j) == 0) {
            ++neighbors;
        }
        if (i < width - 1 && img.getRed(i + 1, j) == 0) {
            ++neighbors;
        }
        if (j > 0 && img.getRed(i, j - 1) == 0) {
            ++neighbors;
        }
        if (j < height - 1 && img.getRed(i, j + 1) == 0) {
            ++neighbors;
        }

        return !(neighbors == 0 || neighbors == 4);
    }

    private void declareRawMoments(int dimension) {
        raw_moments = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                raw_moments[i][j] = -1.0;
            }
        }
    }

    private void declareCentralMoments(int dimension) {
        central_moments = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                central_moments[i][j] = -1.0;
            }
        }
    }

    private double getRawMoment(int p, int q, Image img) {
        if (raw_moments == null) {
            declareRawMoments(10);  //by deafult: 10x10
        }
        if (raw_moments[p][q] != -1.0) {
            return raw_moments[p][q];
        }

        double m = 0;
        width = img.getWidth();
        height = img.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (img.getRed(i, j) == 0) //tylko czarne piksele
                {
                    m += Math.pow(i, p) * Math.pow(j, q);
                }
            }
        }
        raw_moments[p][q] = m;
        return m;
    }

    protected double getCentralMoment(int p, int q, Image img) {
        if (central_moments == null) {
            declareCentralMoments(10);  //by deafult: 10x10
        }
        if (central_moments[p][q] != -1.0) {
            return central_moments[p][q];
        }

        double mc = 0;
        width = img.getWidth();
        height = img.getHeight();
        double m00 = getRawMoment(0, 0, img);
        double m10 = getRawMoment(1, 0, img);
        double m01 = getRawMoment(0, 1, img);
        double x0 = m10 / m00;
        double y0 = m01 / m00;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (img.getRed(i, j) == 0) //tylko czarne piksele
                {
                    mc += Math.pow((i - x0), p) * Math.pow((j - y0), q);
                }
            }
        }
        central_moments[p][q] = mc;
        return mc;
    }

    protected double getNormalizedMoment(int p, int q, Image img) { //zapisuj w tablicy tez central moment
        return getCentralMoment(p, q, img) / Math.pow(getCentralMoment(0, 0, img), (p + q + 2) / 2);
    }

    protected double round(double target, int n) {
        final double tens = Math.pow(10, n);
        return Math.round(target * tens) / tens;
    }

    protected double getPerimeter(Image img) {
        width = img.getWidth();
        height = img.getHeight();
        int[][] num_matrix = new int[width][height]; //0-tło 1-obwód

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (img.getRed(i, j) == 0) {    //for example Red
                    num_matrix[i][j] = isEdge(img, i, j) ? 1 : 0;
                } else {
                    num_matrix[i][j] = 0;
                }
            }
        }

        //konwolucja z maską ułatwiającą liczenie obwodu
        int[] kernel = {10, 2, 10, 2, 1, 2, 10, 2, 10};
        int[] num_output = new int[width * height];   //domyślnie zawiera zera
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int xMin = x - 1;
                int xMax = x + 1;
                int yMin = y - 1;
                int yMax = y + 1;
                for (int r = yMin; r <= yMax; r++) {
                    for (int c = xMin; c <= xMax; c++) {
                        if (r >= 0 && r < height && c >= 0 && c < width) {
                            num_output[y * width + x] += num_matrix[c][r]
                                    * kernel[(r - yMin) * 3 + (c - xMin)];
                        }
                    }
                }
            }
        }

        double perimeter = 0;
        //zliczanie obwodu

        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                final int output = num_output[j * width + i];
                if (output == 5 || output == 7 || output == 15 || output == 17 || output == 25 || output == 27) {
                    ++perimeter;
                } else if (output == 21 || output == 33) {
                    perimeter += SQRT_2;
                } else if (output == 13 || output == 23) {
                    perimeter += C;
                }
            }
            System.out.println();
        }

        return perimeter;
    }

    protected long getArray(Image img) {
        return (long) getRawMoment(0, 0, img);
    }
}
