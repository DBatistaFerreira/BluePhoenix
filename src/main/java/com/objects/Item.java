package com.objects;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("item_id")
    private String itemID;
    @SerializedName("item_type_id")
    private String itemTypeID;
    @SerializedName("item_category_id")
    private String itemCategoryID;
    @SerializedName("is_vehicle_weapon")
    private String isVehicleWeapon;
    @SerializedName("name")
    private ItemName itemName;
    @SerializedName("description")
    private ItemDescription itemDescription;
    @SerializedName("faction_id")
    private String factionID;
    @SerializedName("max_stack_size")
    private String maxStackSize;
    @SerializedName("image_set_id")
    private String imageSetID;
    @SerializedName("image_id")
    private String imageID;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("skill_set_id")
    private String skillSetID;
    @SerializedName("is_default_attachment")
    private String isDefaultAttachment;

    public String getItemID() {
        return itemID;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

    public String getItemCategoryID() {
        return itemCategoryID;
    }

    public String getIsVehicleWeapon() {
        return isVehicleWeapon;
    }

    public ItemName getItemName() {
        return itemName;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public String getFactionID() {
        return factionID;
    }

    public String getMaxStackSize() {
        return maxStackSize;
    }

    public String getImageSetID() {
        return imageSetID;
    }

    public String getImageID() {
        return imageID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSkillSetID() {
        return skillSetID;
    }

    public String getIsDefaultAttachment() {
        return isDefaultAttachment;
    }

    public class ItemName{
        @SerializedName("de")
        private String german;
        @SerializedName("en")
        private String english;
        @SerializedName("es")
        private String spanish;
        @SerializedName("fr")
        private String french;
        @SerializedName("it")
        private String italian;
        @SerializedName("tr")
        private String turkish;

        public String getGerman() {
            return german;
        }

        public String getEnglish() {
            return english;
        }

        public String getSpanish() {
            return spanish;
        }

        public String getFrench() {
            return french;
        }

        public String getItalian() {
            return italian;
        }

        public String getTurkish() {
            return turkish;
        }
    }

    public class ItemDescription{
        @SerializedName("de")
        private String german;
        @SerializedName("en")
        private String english;
        @SerializedName("es")
        private String spanish;
        @SerializedName("fr")
        private String french;
        @SerializedName("it")
        private String italian;
        @SerializedName("tr")
        private String turkish;

        public String getGerman() {
            return german;
        }

        public String getEnglish() {
            return english;
        }

        public String getSpanish() {
            return spanish;
        }

        public String getFrench() {
            return french;
        }

        public String getItalian() {
            return italian;
        }

        public String getTurkish() {
            return turkish;
        }


    }
}
