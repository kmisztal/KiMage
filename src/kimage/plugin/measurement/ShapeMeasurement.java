package kimage.plugin.measurement;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Marcin Chołoniewski
 */
public abstract class ShapeMeasurement extends Plugin {

    private int width, height;
    protected double[][] raw_moments;
    protected double[][] central_moments;

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

    public void declareRawMoments(int dimension) {
        raw_moments = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                raw_moments[i][j] = -1.0;
            }
        }
    }

    public void declareCentralMoments(int dimension) {
        central_moments = new double[dimension][dimension];
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                central_moments[i][j] = -1.0;
            }
        }
    }

    public double getRawMoment(int p, int q, Image img) {
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

    public double getCentralMoment(int p, int q, Image img) {
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

    public double getNormalizedMoment(int p, int q, Image img) { //zapisuj w tablicy tez central moment
        return getCentralMoment(p, q, img) / Math.pow(getCentralMoment(0, 0, img), (p + q + 2) / 2);
    }

    public double round(double target, int n) {
        final double tens = Math.pow(10, n);
        return Math.round(target * tens) / tens;
    }

    public double getPerimeter(Image img) {
        width = img.getWidth();
        height = img.getHeight();
        int[][] num_matrix = new int[width][height]; //0-tło 1-obwód

        double perimeter = 0;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (img.getRed(i, j) == 0) {    //for example Red
                    if (isEdge(img, i, j)) {
                        num_matrix[i][j] = 1;
                    } else {
                        num_matrix[i][j] = 0;
                    }
                } else {
                    num_matrix[i][j] = 0;
                }
            }
        }

        //konwolucja z maską ułatwiającą liczenie obwodu
        int xMin, xMax, yMin, yMax;
        int[] kernel = {10, 2, 10, 2, 1, 2, 10, 2, 10};
        int[] num_output = new int[width * height];   //domyślnie zawiera zera
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                xMin = x - 1;
                xMax = x + 1;
                yMin = y - 1;
                yMax = y + 1;
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

        //zliczanie obwodu
        double a = 1;
        double b = Math.sqrt(2);
        double c = (1 + b) / 2;

        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                if (num_output[j * width + i] == 5 || num_output[j * width + i] == 7
                        || num_output[j * width + i] == 15 || num_output[j * width + i] == 17
                        || num_output[j * width + i] == 25 || num_output[j * width + i] == 27) {
                    perimeter += a;
                } else if (num_output[j * width + i] == 21 || num_output[j * width + i] == 33) {
                    perimeter += b;
                } else if (num_output[j * width + i] == 13 || num_output[j * width + i] == 23) {
                    perimeter += c;
                }
            }
            System.out.println();
        }

        return perimeter;
    }

    public long getArray(Image img) {
        return (long) getRawMoment(0, 0, img);
    }

}
