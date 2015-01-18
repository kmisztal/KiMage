package kimage.plugins.noise;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public class SaltAndPepperNoise extends Plugin {

    private double p;
    
    int check(int v){
        if(v > 255)
            return 255;
        if(v < 0)
            return 0;
        return v;
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        if(getAttribute("p") != null){
            p = (Double) getAttribute("p");
        }else{
            p = 0.2;
            setAttribute("p", p);
        }
        
        int r;
        for (int y = 0; y < imgIn.getHeight(); y++) {
            for (int x = 0; x < imgIn.getWidth(); x++) {
                if(Math.random() < p){
                    if(Math.random() < 0.5){
                        r = 255;
                    }else{
                        r = 0;
                    }
                    imgOut.setRGB(x, y, r, r, r);
                }
            }
        }
    }
}