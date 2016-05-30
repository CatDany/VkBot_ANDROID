package com.test.rmk.vkbot;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

class BotGSON extends Bot {
    private static Logger log = Logger.getLogger(BotGSON.class.getName());

    static JSONObject getJSONObject(String JsonString, String parse) {
        try{
            JSONObject jsonObject = new JSONObject(JsonString);
            log.log(Level.INFO, "getJSONObject method called, returning object");
            return jsonObject.getJSONObject(parse);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        JSONObject nullObject = new JSONObject();
        return nullObject;
    }

    static JSONArray getJSONArray(JSONObject rootObject, String parse) {
        try{
            log.log(Level.INFO, "getJSONArray method called, returning JsonArray");
            return rootObject.getJSONArray(parse);
        } catch(Exception ex){
            ex.printStackTrace();
        }
            JSONArray array = new JSONArray();
        return array;
    }

}
