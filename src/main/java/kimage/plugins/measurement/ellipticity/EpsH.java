package kimage.plugins.measurement.ellipticity;

import kimage.image.Image;
import kimage.plugin.measurement.ShapeMeasurement;

/**
 * @author Marcin Chołoniewski
 */
public class EpsH extends ShapeMeasurement {

    @Override
    public void process(Image imgIn, Image imgOut) {

        final double n20 = getNormalizedMoment(2, 0, imgIn);
        final double n02 = getNormalizedMoment(0, 2, imgIn);
        final double n11 = getNormalizedMoment(1, 1, imgIn);

        final double inv1 = n20 + n02;
        final double inv2 = Math.pow((n20 - n02), 2) + (4 * Math.pow(n11, 2));

        final double ellip = 1 / (2 * Math.pow(Math.PI, 2) *
            (inv1 * Math.sqrt(4 * inv2 + 1 / Math.pow(Math.PI, 2)) - 2 * inv2));

        setAttribute("ellipticity", round(ellip, 4));

    }

}

