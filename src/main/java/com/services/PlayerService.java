package com.services;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.objects.Player;
import com.utilities.RESTConsumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerService {

    private static final String SERVICE_KEY = "s:troopers";
    private static Logger logger = Logger.getLogger(PlayerService.class.getName());
    private static final int PARTITION_SIZE = 350;
    private Player player;
    private HashMap<String, Player> players = new HashMap<>();

    public Player getPlayerByName(String playerName){
        logger.info("Retrieving player for Player Name: " + playerName);
        String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/character/?name.first_lower=" + playerName.toLowerCase() + "&c:resolve=outfit_member_extended");
        convertJSONResponseToPlayer(playerResponse);
        return player;
    }

    public Player getPlayerById(String ID){
        logger.info("Retrieving player for playerID: " + ID);
        String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/character/?character_id=" + ID + "&c:resolve=outfit_member_extended");
        convertJSONResponseToPlayer(playerResponse);
        return player;
    }

    public Map<String, Player> getPlayersByIds(List<String> listOfIDs){
        for(List<String> partition : Lists.partition(listOfIDs, PARTITION_SIZE)) {
            String commaList = getStringListOfIDs(partition);
            String playerResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/character/?character_id=" + commaList + "&c:resolve=outfit_member_extended");
            insertPlayers(playerResponse);
        }
        logger.info("Successfully retrieved all players");
        return players;
    }

    private void insertPlayers(String playerResponse){
        try {
            JSONObject jsonObject = new JSONObject(playerResponse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("character_list"));
            Gson gson = new Gson();
            ArrayList<Player> playerList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                playerList.add(gson.fromJson(jsonArray.get(i).toString(), Player.class));
                Player currentPlayer = playerList.get(i);
                players.put(currentPlayer.getCharacterID(), currentPlayer);
            }
        }
        catch(JSONException e) {
            logger.info("Error in retrieving players");
            logger.info(e.getMessage());
        }
    }

    public static String getStringListOfIDs(List<String> listOfIDs){
        StringBuilder IDs = new StringBuilder();
        if(listOfIDs.size() > 0) {
            IDs.append(listOfIDs.get(0));
            IDs.append(",");
            for (int i = 1; i < listOfIDs.size(); i++) {
                if (i != listOfIDs.size() - 1) {
                    IDs.append(listOfIDs.get(i));
                    IDs.append(",");
                } else {
                    IDs.append(listOfIDs.get(i));
                }
            }
            return IDs.toString();
        }
        else{
            return null;
        }
    }

    private void convertJSONResponseToPlayer(String playerResponse){
        try {
            JSONObject jsonObject = new JSONObject(playerResponse);
            String playerJSON = jsonObject.getString("character_list");
            playerJSON = playerJSON.replace("[", "");
            playerJSON = playerJSON.replace("]", "");
            Gson gson = new Gson();
            player = gson.fromJson(playerJSON, Player.class);
            logger.info("Successfully retrieved player");
        }
        catch(JSONException e){
            logger.info("Error in retrieving player");
            logger.info(e.getMessage());
        }
    }
}
