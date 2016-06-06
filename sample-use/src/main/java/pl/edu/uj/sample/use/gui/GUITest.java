/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.sample.use.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.utils.gui.ImageFrame;

/**
 * @author Krzysztof
 */
public class GUITest {

    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        String filename = "./res/lena.png";

        logger.debug("Image is ready");

        new ImageFrame(new Image(filename)).display();
        }
        }
