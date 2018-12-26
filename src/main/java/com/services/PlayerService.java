package com.services;

import com.google.gson.Gson;
import com.objects.Player;
import com.utilities.RESTConsumer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class PlayerService {
    private static Logger logger = Logger.getLogger(PlayerService.class.getName());
    private Player player;

    public Player getPlayerByName(String playerName){
        logger.info("Retrieving player for Player Name: " + playerName);
        String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/s:troopers/get/ps2:v2/character/?name.first_lower=" + playerName.toLowerCase());
        convertJSONResponseToPlayer(playerResponse);
        return player;
    }

    public Player getPlayerById(String ID){
        logger.info("Retrieving player for playerID: " + ID);
        String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/s:troopers/get/ps2:v2/character/?character_id=" + ID);
        convertJSONResponseToPlayer(playerResponse);
        return player;
    }

    private void convertJSONResponseToPlayer(String playerResponse){
        try {
            JSONObject jsonObject = new JSONObject(playerResponse);
            String playerJSON = jsonObject.getString("character_list");
            playerJSON = playerJSON.replace("[", "");
            playerJSON = playerJSON.replace("]", "");
            Gson gson = new Gson();
            player = gson.fromJson(playerJSON, Player.class);
            logger.info("Successfully retrieved player: " + player.getCharacterName().getName());
        }
        catch(JSONException e){
            logger.info("Error in retrieving player for Player Name: " + player.getCharacterName().getName());
            logger.info(e.getMessage());
        }
    }
}
