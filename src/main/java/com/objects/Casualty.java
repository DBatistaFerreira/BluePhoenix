package com.objects;

import com.google.gson.annotations.SerializedName;

public class Casualty {
    @SerializedName("character_id")
    private String characterID;
    @SerializedName("attacker_character_id")
    private String attackerCharacterID;
    @SerializedName("is_headshot")
    private String isHeadshot;
    @SerializedName("attacker_weapon_id")
    private String attackerWeaponID;
    @SerializedName("attacker_vehicle_id")
    private String attackerVehicleID;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("zone_id")
    private String zoneID;
    @SerializedName("world_id")
    private String worldID;
    @SerializedName("table_type")
    private String tableType;

    public String getCharacterID() {
        return characterID;
    }

    public String getAttackerCharacterID() {
        return attackerCharacterID;
    }

    public String isHeadshot(){
        if("0".equals(isHeadshot)){
            return "true";
        }
        else {
            return "false";
        }
    }

    public String getAttackerWeaponID() {
        return attackerWeaponID;
    }

    public String getAttackerVehicleID() {
        return attackerVehicleID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getZoneID() {
        return zoneID;
    }

    public String getWorldID() {
        return worldID;
    }

    public String getTableType() {
        return tableType;
    }
}
