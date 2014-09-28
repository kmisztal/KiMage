package test;

import kimage.image.Image;

/**
 *
 * @author Krzysztof
 */
public class LoadAndShowAndSaveTest {

    public static void main(String[] args) {
        // load file
        String filename = "./res/tucano.jpg";
        Image image = new Image(filename);

        //save results
        image.save("./res/out.png");
    }
}
