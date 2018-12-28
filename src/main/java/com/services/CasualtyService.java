package com.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.objects.Casualty;
import com.utilities.RESTConsumer;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CasualtyService {

    private static Logger logger = Logger.getLogger(CasualtyService.class.getName());
    private List<Casualty> casualties = new LinkedList<>();

    public List<Casualty> getCasualities(String characterID){
        logger.info("Retrieving all casualities for chracterID: " + characterID);
        String characterEventJson = RESTConsumer.get("http://census.daybreakgames.com/s:troopers/get/ps2:v2/characters_event/?character_id=" + characterID + "&type=KILL,DEATH&c:limit=1000");
        try {
            Map<String, Object> characterEventListMap = new Gson().fromJson(characterEventJson, new TypeToken<HashMap<String, Object>>() {}.getType());
            String casualtiesString = characterEventListMap.get("characters_event_list").toString();
            JSONArray jsonArray = new JSONArray(casualtiesString);
            Gson gson = new Gson();
            for(int i = 0; i < jsonArray.length(); i++) {
                casualties.add(gson.fromJson(jsonArray.get(i).toString(), Casualty.class));
            }
            logger.info("Successfully retrieved " + jsonArray.length() + " casualities for characterID: " + characterID);
        }
        catch (JSONException e) {
            logger.info("Error in retrieving casualities for characterID: " + characterID);
            logger.info(e.getMessage());
        }
        return casualties;
    }
}
