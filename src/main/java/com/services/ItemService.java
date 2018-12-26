package com.services;

import com.google.gson.Gson;
import com.objects.Item;
import com.utilities.RESTConsumer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class ItemService {
    private static Logger logger = Logger.getLogger(ItemService.class.getName());
    private Item item;

    public Item getItemById(String itemID){
        logger.info("Retrieving item for Item ID: " + itemID);
        try {
        String itemResponse = RESTConsumer.get("http://census.daybreakgames.com/get/ps2:v2/item?item_id=" + itemID);
        JSONObject jsonObject = new JSONObject(itemResponse);
        String itemJSON = jsonObject.getString("item_list");
        itemJSON = itemJSON.replace("[", "");
        itemJSON = itemJSON.replace("]", "");
        Gson gson = new Gson();
        item = gson.fromJson(itemJSON, Item.class);
        logger.info("Successfully retrieved Item: " + item.getItemName().getEnglish());
        }
        catch(JSONException e){
            logger.info("Error in retrieving item for Item ID: " + itemID);
            logger.info(e.getMessage());
        }
        return item;
    }

    public Image getItemImage(String imageID){
        try {
            logger.info("Retrieving item image for Image ID: " + imageID);
            URL url = new URL("https://census.daybreakgames.com/files/ps2/images/static/" + imageID + ".png");
            BufferedImage image = ImageIO.read(url);
            Image itemImage = SwingFXUtils.toFXImage(image, null);
            logger.info("Retrieving item for Image ID: " + imageID);
            return itemImage;
        } catch (IOException e) {
            logger.info("Error in retrieving item for Image ID: " + imageID);
            logger.info(e.getMessage());
        }
        return null;
    }
}
