package test;

import kimage.image.Image;
import kimage.plugins.Plugin;
import kimage.plugins.color.SimpleInvert;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Krzysztof
 */
public class PluginTest {
    public static void main(String[] args) {
        // load file
        String filename = "./res/lena.png";
        Image image = new Image(filename);

        //display image
        ImageFrame before = new ImageFrame(image);
        before.display();
        
        //Load plugin
        Plugin plugin = new SimpleInvert();
        plugin.process(image, image);
        
        //display image
        ImageFrame after = new ImageFrame(image);
        after.display();
        
        //save results
        image.save("./res/out.png");
    }
}
