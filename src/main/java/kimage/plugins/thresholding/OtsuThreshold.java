package kimage.plugins.thresholding;

import kimage.image.Image;
import kimage.plugin.thresholding.ThresholdPlugin;

/**
 * @author Krzysztof
 */
public class OtsuThreshold extends ThresholdPlugin {

    private static int otsuTreshold(Image image, boolean weight) {
        double[] histogram = imageHistogram(image);
        int total = image.getWidth() * image.getHeight();

        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        float sumB = 0;
        int wB = 0,
                wF;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            if (weight) {
                histogram[i] = histogram[i] * Math.exp(-i / 100.);
            }

            wB += histogram[i];
            if (wB == 0) {
                continue;
            }
            wF = total - wB;

            if (wF == 0) {
                break;
            }

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        if (threshold == 0) {
            ++threshold;
        }
        return threshold;
    }

    // Return histogram of grayscale image
    private static double[] imageHistogram(Image image) {
        double[] histogram = new double[256];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                histogram[image.getRed(i, j)]++;
            }
        }
        return histogram;
    }

    @Override
    public int getThreshold(Image image) {
        return otsuTreshold(image, false);
    }
}
