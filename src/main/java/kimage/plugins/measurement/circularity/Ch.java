package kimage.plugins.measurement.circularity;

import kimage.image.Image;
import kimage.plugin.measurement.ShapeMeasurement;

/**
 * @author Marcin Chołoniewski
 */
public class Ch extends ShapeMeasurement {

    @Override
    public void process(Image imgIn, Image imgOut) {
        final long array = getArray(imgIn);
        final double circ = (array * array) /
            (2.0 * Math.PI * (getCentralMoment(2, 0, imgIn) + getCentralMoment(0, 2, imgIn)));

        setAttribute("circularity", round(circ, 4));
    }

}
