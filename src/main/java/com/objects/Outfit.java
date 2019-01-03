package com.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outfit {

    @SerializedName("outfit_id")
    private String ID;
    @SerializedName("name")
    private String name;
    @SerializedName("alias")
    private String alias;
    @SerializedName("time_created")
    private String timeCreated;
    @SerializedName("leader_character_id")
    private String leaderCharacterID;
    @SerializedName("member_count")
    private String memberCount;
    @SerializedName("members")
    private List<OutfitMember> members;
    @SerializedName("leader")
    private Leader leader;

    public String getID() {
        return ID;
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

    public String getLeaderCharacterID() {
        return leaderCharacterID;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public List<OutfitMember> getMembers() {
        return members;
    }

    public Leader getLeader() {
        return leader;
    }

    public class Leader{
        @SerializedName("world_id")
        private String worldID;
        @SerializedName("name")
        private Player.Name name;
        @SerializedName("faction_id")
        private String factionId;

        public String getWorldID() {
            return worldID;
        }

        public Player.Name getName() {
            return name;
        }

        public String getFactionId() {
            return factionId;
        }
    }

    public class OutfitMember{
        @SerializedName("character_id")
        private String characterID;
        @SerializedName("member_since")
        private String memberSince;
        @SerializedName("rank")
        private String rank;
        @SerializedName("rank_ordinal")
        private String rankOrdinal;
        @SerializedName("character")
        private Player player;

        public String getCharacterID() {
            return characterID;
        }

        public String getMemberSince() {
            return memberSince;
        }

        public String getRank() {
            return rank;
        }

        public String getRankOrdinal() {
            return rankOrdinal;
        }

        public Player getPlayer() {
            return player;
        }
    }
}
