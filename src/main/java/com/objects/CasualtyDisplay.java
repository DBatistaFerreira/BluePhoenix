package com.objects;

public class CasualtyDisplay {
    private String targetCharacterName;
    private String attackerCharacterName;
    private String isHeadshot;
    private String attackerWeaponName;
    private String dateLocalTime;
    private String continent;
    private String tableType;

    public CasualtyDisplay() {
    }

    public CasualtyDisplay(String targetCharacterName, String attackerCharacterName, String isHeadshot, String attackerWeaponName, String dateLocalTime, String continent, String tableType) {
        this.targetCharacterName = targetCharacterName;
        this.attackerCharacterName = attackerCharacterName;
        this.isHeadshot = isHeadshot;
        this.attackerWeaponName = attackerWeaponName;
        this.dateLocalTime = dateLocalTime;
        this.continent = continent;
        this.tableType = tableType;
    }

    public String getTargetCharacterName() {
        return targetCharacterName;
    }

    public String getAttackerCharacterName() {
        return attackerCharacterName;
    }

    public String getIsHeadshot() {
        return isHeadshot;
    }

    public String getAttackerWeaponName() {
        return attackerWeaponName;
    }

    public String getDateLocalTime() {
        return dateLocalTime;
    }

    public String getContinent() {
        return continent;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTargetCharacterName(String targetCharacterName) {
        this.targetCharacterName = targetCharacterName;
    }

    public void setAttackerCharacterName(String attackerCharacterName) {
        this.attackerCharacterName = attackerCharacterName;
    }

    public void setIsHeadshot(String isHeadshot) {
        this.isHeadshot = isHeadshot;
    }

    public void setAttackerWeaponName(String attackerWeaponName) {
        this.attackerWeaponName = attackerWeaponName;
    }

    public void setDateLocalTime(String dateLocalTime) {
        this.dateLocalTime = dateLocalTime;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
}
