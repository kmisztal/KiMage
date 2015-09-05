/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use;

import kimage.image.Image;
import kimage.utils.gui.ImageFrame;

/**
 *
 * @author Krzysztof
 */
public class GUITest {
    public static void main(String[] args) {
        String filename = "./res/lena.png";
        
        new ImageFrame(new Image(filename)).display();
    }
}
