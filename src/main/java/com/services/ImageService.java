package com.services;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class ImageService {

    private static Logger logger = Logger.getLogger(ImageService.class.getName());

    public Image getImage(String imageID){
        try {
            logger.info("Retrieving image for Image ID: " + imageID);
            URL url = new URL("https://census.daybreakgames.com/files/ps2/images/static/" + imageID + ".png");
            BufferedImage image = ImageIO.read(url);
            Image itemImage = SwingFXUtils.toFXImage(image, null);
            logger.info("Retrieving image for Image ID: " + imageID);
            return itemImage;
        } catch (IOException e) {
            logger.info("Error in retrieving image for Image ID: " + imageID);
            logger.info(e.getMessage());
        }
        return null;
    }
}
