package com.services;

import com.google.gson.Gson;
import com.objects.Outfit;
import com.utilities.RESTConsumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class OutfitService {

    private static Logger logger = Logger.getLogger(PlayerService.class.getName());
    private static final String SERVICE_KEY = "s:troopers";
    private Outfit outfit;

    public Outfit getOutfit(String alias){
        logger.info("Retrieving Outfit for Alias: " + alias);
        String outfitResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/outfit/?alias_lower=" + alias.toLowerCase() + "&c:resolve=member_characters_stat_history(stat_name,all_time)&c:join=outfit_member%5Eon:outfit_id%5Einject_at:members%5Elist:1%5Eshow:character_id(characters_online_status%5Eon:character_id%5Einject_at:character%5Eshow:online_status,character%5Eon:character_id%5Einject_at:character%5Eshow:name.first%27times.last_save%27prestige_level%27battle_rank),characters_world%5Eon:leader_character_id%5Eto:character_id%5Einject_at:leader%5Eshow:world_id,character%5Eon:leader_character_id%5Eto:character_id%5Einject_at:leader%5Eshow:name.first%27faction_id&c:hide=name_lower,alias_lower,time_created_date");
        try {
            JSONObject outfitJsonObject = new JSONObject(outfitResponse);
            JSONArray outfitJsonArray = new JSONArray(outfitJsonObject.getString("outfit_list"));
            Gson gson = new Gson();
            outfit = gson.fromJson(outfitJsonArray.get(0).toString(), Outfit.class);
            logger.info("Successfully retrieved Outfit for Alias: " + alias);
        }catch(JSONException e){
            logger.info("Error in retrieving Outfit for Alias: " + alias);
        }
        return outfit;
    }
}
