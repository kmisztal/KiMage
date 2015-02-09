package kimage.plugins.thresholding;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public class MultiOtsuThreshold extends Plugin {

    static final int NGRAY = 256;
    private int MLEVEL = 3;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute("levels") != null) {
            MLEVEL = (int) getAttribute("levels");
        }
        if (MLEVEL > 5 || MLEVEL < 2) {
            System.err.println(this.getClass().getName() + ": ERROR: Wrong threshold number, should be 2, 3, 4 or 5");
            return;
        }

//        System.out.println(this.getClass().getName() + ": Levels number " + MLEVEL);
        int[] threshold = new int[MLEVEL]; // threshold
        ////////////////////////////////////////////
        // Build Histogram
        ////////////////////////////////////////////
        float[] h = buildHistogram(imgIn);

        /////////////////////////////////////////////
        // Build lookup tables from h
        ////////////////////////////////////////////
        float[][] P = new float[NGRAY][NGRAY];
        float[][] S = new float[NGRAY][NGRAY];
        float[][] H = new float[NGRAY][NGRAY];
        buildLookupTables(P, S, H, h);

        ////////////////////////////////////////////////////////
        // now M level loop   MLEVEL dependent term
        ////////////////////////////////////////////////////////
        float maxSig = findMaxSigma(MLEVEL, H, threshold);
        int[] ret = new int[MLEVEL + 1];
        ret[0] = 0;
        ret[ret.length - 1] = 256;

        for (int i = 1; i < MLEVEL; ++i) {
            ret[i] = threshold[i];
        }

        setAttribute("thresholds", ret);

        ///////////////////////////////////////////////////////////////
        // show regions works for any MLEVEL
        ///////////////////////////////////////////////////////////////
//        showRegions(MLEVEL, threshold, pixels, width, height);
    }

    public int[] getLevels() {
        if (getAttribute("thresholds") != null) {
            return (int[]) getAttribute("thresholds");
        }
        return null;
    }

    private float[] buildHistogram(Image image) {
        float[] h = new float[NGRAY];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                ++h[image.getRed(x, y)];
            }
        }

//        System.out.println(Arrays.toString(h));
        return h;
    }

    public void buildLookupTables(float[][] P, float[][] S, float[][] H, float[] h) {
        // initialize
        for (int j = 0; j < NGRAY; j++) {
            for (int i = 0; i < NGRAY; ++i) {
                P[i][j] = 0.f;
                S[i][j] = 0.f;
                H[i][j] = 0.f;
            }
        }
        // diagonal 
        for (int i = 1; i < NGRAY; ++i) {
            P[i][i] = h[i];
            S[i][i] = ((float) i) * h[i];
        }
        // calculate first row (row 0 is all zero)
        for (int i = 1; i < NGRAY - 1; ++i) {
            P[1][i + 1] = P[1][i] + h[i + 1];
            S[1][i + 1] = S[1][i] + ((float) (i + 1)) * h[i + 1];
        }
        // using row 1 to calculate others
        for (int i = 2; i < NGRAY; i++) {
            for (int j = i + 1; j < NGRAY; j++) {
                P[i][j] = P[1][j] - P[1][i - 1];
                S[i][j] = S[1][j] - S[1][i - 1];
            }
        }
        // now calculate H[i][j]
        for (int i = 1; i < NGRAY; ++i) {
            for (int j = i + 1; j < NGRAY; j++) {
                if (P[i][j] != 0) {
                    H[i][j] = (S[i][j] * S[i][j]) / P[i][j];
                } else {
                    H[i][j] = 0.f;
                }
            }
        }

    }

    public float findMaxSigma(int mlevel, float[][] H, int[] t) {
        t[0] = 0;
        float maxSig = 0.f;
        switch (mlevel) {
            case 2:
                for (int i = 1; i < NGRAY - mlevel; ++i) // t1
                {
                    final float Sq = H[1][i] + H[i + 1][255];
                    if (maxSig < Sq) {
                        t[1] = i;
                        maxSig = Sq;
                    }
                }
                break;
            case 3:
                for (int i = 1; i < NGRAY - mlevel; ++i) // t1
                {
                    for (int j = i + 1; j < NGRAY - mlevel + 1; ++j) // t2
                    {
                        final float Sq = H[1][i] + H[i + 1][j] + H[j + 1][255];
                        if (maxSig < Sq) {
                            t[1] = i;
                            t[2] = j;
                            maxSig = Sq;
                        }
                    }
                }
                break;
            case 4:
                for (int i = 1; i < NGRAY - mlevel; ++i) // t1
                {
                    for (int j = i + 1; j < NGRAY - mlevel + 1; ++j) // t2
                    {
                        for (int k = j + 1; k < NGRAY - mlevel + 2; ++k) // t3
                        {
                            final float Sq = H[1][i] + H[i + 1][j] + H[j + 1][k] + H[k + 1][255];
                            if (maxSig < Sq) {
                                t[1] = i;
                                t[2] = j;
                                t[3] = k;
                                maxSig = Sq;
                            }
                        }
                    }
                }
                break;
            case 5:
                for (int i = 1; i < NGRAY - mlevel; ++i) // t1
                {
                    for (int j = i + 1; j < NGRAY - mlevel + 1; ++j) // t2
                    {
                        for (int k = j + 1; k < NGRAY - mlevel + 2; ++k) // t3
                        {
                            for (int m = k + 1; m < NGRAY - mlevel + 3; ++m) // t4
                            {
                                final float Sq = H[1][i] + H[i + 1][j] + H[j + 1][k] + H[k + 1][m] + H[m + 1][255];
                                if (maxSig < Sq) {
                                    t[1] = i;
                                    t[2] = j;
                                    t[3] = k;
                                    t[4] = m;
                                    maxSig = Sq;
                                }
                            }
                        }
                    }
                }
                break;
        }
        return maxSig;
    }

}
