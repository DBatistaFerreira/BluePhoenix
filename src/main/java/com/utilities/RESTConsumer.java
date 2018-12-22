package com.utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

//Service Class
public class RESTConsumer {

    private static Logger logger = Logger.getLogger(RESTConsumer.class.getName());
    private static final int SUCCESS = 200;
    private static final String MIME_TYPE = "application/json";

    private RESTConsumer(){}

    public static JSONArray get(String uri){
        try{
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Establish Connection to API
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", MIME_TYPE);
            if(httpURLConnection.getResponseCode() != SUCCESS){
               logger.info("HTTP Failure: HTTP Error Code: " + httpURLConnection.getResponseCode());
               return null;
            }
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream()); //Stream to get data from response
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String jsonLine;
            while(!Objects.isNull((jsonLine = bufferedReader.readLine()))){ //Get Response JSON
                stringBuilder.append(jsonLine);
            }
            httpURLConnection.disconnect();
            inputStreamReader.close();
            bufferedReader.close();
            return new JSONArray("[" + stringBuilder.toString() + "]"); //Needs to be enclosed by [ ] to recognize JSON
        }
        catch(IOException | JSONException e){
            logger.info(e.getMessage());
            return null;
        }
    }
}
