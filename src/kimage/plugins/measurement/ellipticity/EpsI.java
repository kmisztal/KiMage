package kimage.plugins.measurement.ellipticity;

import kimage.image.Image;
import kimage.plugin.measurement.ShapeMeasurement;

/**
 *
 * @author Marcin Chołoniewski
 */
public class EpsI extends ShapeMeasurement {

    @Override
    public void process(Image imgIn, Image imgOut) {
        double ellip = 0;
        final double m20 = getCentralMoment(2, 0, imgIn);
        final double m02 = getCentralMoment(0, 2, imgIn);
        final double m11 = getCentralMoment(1, 1, imgIn);
        final double m00 = getCentralMoment(0, 0, imgIn);

        final double invariant = (m20 * m02 - Math.pow(m11, 2)) / Math.pow(m00, 4);

        final double value = 16 * Math.pow(Math.PI, 2);

        if(invariant <= 1 / value)
            ellip = value * invariant;
        else
            ellip = 1 / (value * invariant);

        setAttribute("ellipticity", round(ellip, 4));
    }
    
}
