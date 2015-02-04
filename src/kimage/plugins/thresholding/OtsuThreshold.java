package kimage.plugins.thresholding;

import kimage.plugin.thresholding.ThresholdPlugin;

/**
 *
 * @author Krzysztof
 */
public class OtsuThreshold extends ThresholdPlugin{

    @Override
    public int getThreshold() {
        return 128;
    }
    
}
