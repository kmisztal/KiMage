package kimage.plugins.util.smallestellipse;

import java.awt.Color;
import java.awt.Graphics2D;
import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.tools.draw.EllipseDraw;
import kimage.tools.math.clusters.Cluster;
import kimage.tools.math.matrix.Matrix;

/**
 *
 * @author Krzysztof
 */
public class SimpleSmallestEllipse extends Plugin {

    private Cluster shape;

    @Override
    public void process(Image imgIn, Image imgOut) {
        final int height = imgIn.getHeight();
        final int widht = imgIn.getWidth();

        double vol = 1;
        double cvol[];
        int it = 0;

        final double d = 0;
        do {

            shape = new Cluster(2);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < widht; x++) {
                    try {
                        final int val = imgIn.getRed(x, y);
                        if (val == 0) {
                            shape.addTo(new Cluster(1., 1., x + d, y + d));
                        }
                    } catch (Cluster.ClusterException ex) {
                    }
                }
            }
            final Graphics2D g2s = (Graphics2D) imgIn.getBufferedImage().createGraphics();
            cvol = calc(shape);
            System.out.print(it++ + "\t" + cvol[0] / vol + "\t");
            final double x0 = cvol[1];
            final double y0 = cvol[2];
            final double ewidth = cvol[3];
            final double eheight = cvol[4];
            final double phi = cvol[5];

            System.out.println(vol + "\t" + x0 + "\t" + y0 + "\t" + ewidth / 2 + "\t" + eheight / 2 + "\t" + phi);
            if (cvol[0] / vol > 1.001) {
                EllipseDraw.fill(g2s, new Color(0, 0, 0), phi,
                        (int) Math.round(x0 - ewidth / 2.), (int) Math.round(y0 - eheight / 2.),
                        (int) Math.round(ewidth), (int) Math.round(eheight));
                vol = cvol[0];
            } else {
                EllipseDraw.fill((Graphics2D) imgOut.getBufferedImage().createGraphics(), new Color(50, 50, 50, 100), phi,
                        (int) Math.round(x0 - ewidth / 2.), (int) Math.round(y0 - eheight / 2.),
                        (int) Math.round(ewidth), (int) Math.round(eheight));
                break;
            }
        } while (true);

    }

    public static double[] calc(Cluster shape) {
        final double width;
        final double height;
        final double phi;
        final double x0;
        final double y0;
        final Matrix cov = shape.getCov();
        double[][] v = cov.eig(true);
        width = 4. * Math.sqrt(v[1][0]);
        height = 4. * Math.sqrt(v[0][0]);
        x0 = shape.getMean().get(0, 0);
        y0 = shape.getMean().get(1, 0);

        v = cov.eig(false);
        phi = Math.atan2(v[0][1], v[0][0]) + Math.PI / 2.;

        final double vol = width * height * 2. * Math.PI / 4;

        return new double[]{vol, x0, y0, width, height, phi};
    }
}
