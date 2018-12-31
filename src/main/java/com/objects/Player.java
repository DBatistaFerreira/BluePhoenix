package com.objects;

import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("character_id")
    private String characterID;
    @SerializedName("name")
    private Name characterName;
    @SerializedName("faction_id")
    private String factionID;
    @SerializedName("head_id")
    private String headID;
    @SerializedName("title_id")
    private String titleID;
    @SerializedName("times")
    private Times playerTimes;
    @SerializedName("certs")
    private Certs certs;
    @SerializedName("battle_rank")
    private BattleRank battleRank;
    @SerializedName("profile_id")
    private String profileID;
    @SerializedName("daily_ribbon")
    private DailyRibbon dailyRibbon;
    @SerializedName("prestige_level")
    private String prestigeLevel;
    @SerializedName("outfit_member")
    private OutfitMember outfitMember;

    public String getCharacterID() {
        return characterID;
    }

    public Name getCharacterName() {
        return characterName;
    }

    public String getFactionID() {
        return factionID;
    }

    public String getHeadID() {
        return headID;
    }

    public String getTitleID() {
        return titleID;
    }

    public Times getPlayerTimes() {
        return playerTimes;
    }

    public Certs getCerts() {
        return certs;
    }

    public BattleRank getBattleRank() {
        return battleRank;
    }

    public String getProfileID() {
        return profileID;
    }

    public DailyRibbon getDailyRibbon() {
        return dailyRibbon;
    }

    public String getPrestigeLevel() {
        return prestigeLevel;
    }

    public OutfitMember getOutfitMember() {
        return outfitMember;
    }

    public class OutfitMember{

        @SerializedName("member_since")
        private String memberSince;
        @SerializedName("member_rank")
        private String memberRank;
        @SerializedName("member_rank_ordinal")
        private String memberRankOrdinal;
        @SerializedName("outfit_id")
        private String outfitID;
        @SerializedName("name")
        private String name;
        @SerializedName("alias")
        private String alias;
        @SerializedName("time_created")
        private String timeCreated;
        @SerializedName("leader_character_id")
        private String leaderCharacterId;
        @SerializedName("member_count")
        private String memberCount;

        public String getMemberSince() {
            return memberSince;
        }

        public String getMemberRank() {
            return memberRank;
        }

        public String getMemberRankOrdinal() {
            return memberRankOrdinal;
        }

        public String getOutfitID() {
            return outfitID;
        }

        public String getName() {
            return name;
        }

        public String getAlias() {
            return alias;
        }

        public String getTimeCreated() {
            return timeCreated;
        }

        public String getLeaderCharacterId() {
            return leaderCharacterId;
        }

        public String getMemberCount() {
            return memberCount;
        }
    }

    public class BattleRank{
        @SerializedName("percent_to_next")
        private String percentToNext;
        @SerializedName("value")
        private String battleRankValue;

        public String getPercentToNext() {
            return percentToNext;
        }

        public String getBattleRankValue() {
            return battleRankValue;
        }
    }

    public class DailyRibbon{
        @SerializedName("count")
        private String count;
        @SerializedName("time")
        private String time;

        public String getCount() {
            return count;
        }

        public String getTime() {
            return time;
        }
    }

    public class Certs{
        @SerializedName("earned_points")
        private String earnedPoints;
        @SerializedName("gifted_points")
        private String giftedPoints;
        @SerializedName("spent_points")
        private String spentPoints;
        @SerializedName("available_points")
        private String availablePoints;
        @SerializedName("percent_to_next")
        private String percentToNext;

        public String getEarnedPoints() {
            return earnedPoints;
        }

        public String getGiftedPoints() {
            return giftedPoints;
        }

        public String getSpentPoints() {
            return spentPoints;
        }

        public String getAvailablePoints() {
            return availablePoints;
        }

        public String getPercentToNext() {
            return percentToNext;
        }
    }

    public class Times{
        @SerializedName("creation")
        private String creationDate;
        @SerializedName("last_save")
        private String lastSaveDate;
        @SerializedName("last_login")
        private String lastLoginDate;
        @SerializedName("login_count")
        private String loginCount;
        @SerializedName("minutes_played")
        private String minutesPlayed;

        public String getCreationDate() {
            return creationDate;
        }

        public String getLastSaveDate() {
            return lastSaveDate;
        }

        public String getLastLoginDate() {
            return lastLoginDate;
        }

        public String getLoginCount() {
            return loginCount;
        }

        public String getMinutesPlayed() {
            return minutesPlayed;
        }
    }

    public class Name{
        @SerializedName("first")
        private String name;
        @SerializedName("first_lower")
        private String nameLowerCase;

        public String getName() {
            return name;
        }

        public String getNameLowerCase() {
            return nameLowerCase;
        }
    }


}
