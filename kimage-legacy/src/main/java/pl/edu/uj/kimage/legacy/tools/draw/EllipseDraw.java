package pl.edu.uj.kimage.legacy.tools.draw;

import pl.edu.uj.kimage.legacy.tools.math.clusters.Cluster;
import pl.edu.uj.kimage.legacy.tools.math.matrix.Matrix;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Krzysztof
 */
public class EllipseDraw {

    public static void fill(Graphics2D g2, Color color,
                            double angle,
                            int x0, int y0,
                            int width, int height) {
        draw(g2, color, angle, x0, y0, width, height, true);
    }

    public static void draw(Graphics2D g2, Color color,
                            double angle,
                            int x0, int y0,
                            int width, int height) {
        draw(g2, color, angle, x0, y0, width, height, false);
    }

    public static void draw(Graphics2D g2, Color color,
                            double angle,
                            int x0, int y0,
                            int width, int height, boolean fill) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Shape r1 = new Ellipse2D.Float(x0, y0, width, height);
        g2.rotate(angle, x0 + width / 2, y0 + height / 2);
        g2.setStroke(new BasicStroke(1));
        g2.setPaint(color);
        if (fill) {
            g2.fill(r1);
        } else {
            g2.draw(r1);
        }
    }

    public static void fillCirlce(Graphics2D g2s, Color color, Cluster shape) {
        final double r;
        final double phi = 0.;
        final double x0;
        final double y0;
        final Matrix cov = shape.getCov();
        r = 2. * Math.sqrt(2. * (cov.get(0, 0) + cov.get(1, 1)));
        x0 = shape.getMean().get(0, 0);
        y0 = shape.getMean().get(1, 0);

        draw(g2s, color, phi,
                (int) Math.round(x0 - r / 2.),
                (int) Math.round(y0 - r / 2.),
                (int) Math.round(r), (int) Math.round(r), true);
    }

    public static void drawCirlce(Graphics2D g2s, Color color, Cluster shape) {
        final double r;
        final double phi = 0.;
        final double x0;
        final double y0;
        final Matrix cov = shape.getCov();
        r = 2. * Math.sqrt(2. * (cov.get(0, 0) + cov.get(1, 1)));
        x0 = shape.getMean().get(0, 0);
        y0 = shape.getMean().get(1, 0);

        draw(g2s, color, phi,
                (int) Math.round(x0 - r / 2.),
                (int) Math.round(y0 - r / 2.),
                (int) Math.round(r), (int) Math.round(r), false);
    }

}
