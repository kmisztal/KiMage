package test;

import kimage.image.Image;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Krzysztof
 */
public class LoadAndShowAndSaveTest {

    public static void main(String[] args) {
        // load file
        String filename = "./res/tucano.jpg";
        Image image = new Image(filename);

        //display image
        ImageFrame imageFrame = new ImageFrame(image);
        imageFrame.display();
        
        //save results
        image.save("./res/out.png");
    }
}
