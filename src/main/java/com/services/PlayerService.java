package com.services;

import com.google.gson.Gson;
import com.objects.Player;
import com.utilities.RESTConsumer;
import org.json.JSONObject;

import java.util.logging.Logger;

public class PlayerService {
    private static Logger logger = Logger.getLogger(RESTConsumer.class.getName());
    private Player player;

    public Player getPlayer(String playerName){
        logger.info("Retrieving player for playerID: " + playerName);
        String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/s:troopers/get/ps2:v2/character/?name.first_lower=" + playerName.toLowerCase());
        try {
            JSONObject jsonObject = new JSONObject(playerResponse);
            String playerJSON = jsonObject.getString("character_list");
            playerJSON = playerJSON.replace("[", "");
            playerJSON = playerJSON.replace("]", "");
            Gson gson = new Gson();
            player = gson.fromJson(playerJSON, Player.class);
            logger.info("Successfully retrieved player: " + playerName);
        }
        catch (Exception e) {
            logger.info("Error in retrieving player for Player Name: " + playerName);
            logger.info(e.getMessage());
        }
        return player;
    }
}
