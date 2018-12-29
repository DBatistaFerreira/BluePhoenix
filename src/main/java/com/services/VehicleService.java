package com.services;

import com.google.gson.Gson;
import com.objects.Vehicle;
import com.utilities.RESTConsumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VehicleService {

    private static final String SERVICE_KEY = "s:troopers";
    private static Logger logger = Logger.getLogger(VehicleService.class.getName());
    private Vehicle vehicle;
    private HashMap<String, Vehicle> vehicles = new HashMap<>();

    public Vehicle getVehicleById(String ID){
        logger.info("Retrieving vehicle for vehicle ID: " + ID);
        String vehicleResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/vehicle?vehicle_id=" + ID);
        convertJSONResponseToVehicle(vehicleResponse);
        return vehicle;
    }

    public Map<String, Vehicle> getVehiclesByIds(List<String> listOfIDs){
        String commaList = PlayerService.getStringListOfIDs(listOfIDs);
        logger.info("Retrieving vehicles for vehicle IDs: " + commaList);
        try {
            String vehicleResponse = RESTConsumer.get("http://census.daybreakgames.com/" + SERVICE_KEY + "/get/ps2:v2/vehicle?vehicle_id=" + commaList);
            JSONObject jsonObject = new JSONObject(vehicleResponse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("vehicle_list"));
            Gson gson = new Gson();
            ArrayList<Vehicle> vehicleList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++) {
                vehicleList.add(gson.fromJson(jsonArray.get(i).toString(), Vehicle.class));
                Vehicle currentVehicle = vehicleList.get(i);
                vehicles.put(currentVehicle.getVehicleID(), currentVehicle);
            }
            logger.info("Successfully retrieved all vehicles");
            return vehicles;
        }
        catch(JSONException e){
            logger.info("Error in retrieving vehicles");
            logger.info(e.getMessage());
        }
        return null;
    }

    private void convertJSONResponseToVehicle(String vehicleResponse){
        try {
            JSONObject jsonObject = new JSONObject(vehicleResponse);
            String vehicleJSON = jsonObject.getString("vehicle_list");
            vehicleJSON = vehicleJSON.replace("[", "");
            vehicleJSON = vehicleJSON.replace("]", "");
            Gson gson = new Gson();
            vehicle = gson.fromJson(vehicleJSON, Vehicle.class);
            logger.info("Successfully retrieved vehicle");
        }
        catch(JSONException e){
            logger.info("Error in retrieving vehicle");
            logger.info(e.getMessage());
        }
    }
}
