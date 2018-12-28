package com.objects;

import com.google.gson.annotations.SerializedName;

public class Vehicle {
    @SerializedName("vehicle_id")
    private String vehicleID;
    @SerializedName("name")
    private VehicleName vehicleName;
    @SerializedName("description")
    private VehicleDescription vehicleDescription;
    @SerializedName("type_id")
    private String typeID;
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("cost")
    private String cost;
    @SerializedName("cost_resource_id")
    private String costResourceID;
    @SerializedName("image_set_id")
    private String imageSetId;
    @SerializedName("image_id")
    private String imageID;
    @SerializedName("image_path")
    private String imagePath;

    public String getVehicleID() {
        return vehicleID;
    }

    public VehicleName getVehicleName() {
        return vehicleName;
    }

    public VehicleDescription getVehicleDescription() {
        return vehicleDescription;
    }

    public String getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getCost() {
        return cost;
    }

    public String getCostResourceID() {
        return costResourceID;
    }

    public String getImageSetId() {
        return imageSetId;
    }

    public String getImageID() {
        return imageID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public class VehicleName{
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

    public class VehicleDescription{
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
