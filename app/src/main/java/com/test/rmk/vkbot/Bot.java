package com.test.rmk.vkbot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Bot {
    private static Logger log = Logger.getLogger(Bot.class.getName());

    boolean whoIsUser(String groupMessage, VKAPI vkapi, String wantedInfo, String wantedResponse) throws IOException{
        try{
            JSONObject rootObject = BotGSON.getJSONObject(groupMessage, "response");
            JSONArray array = rootObject.getJSONArray("items");
                //BotGSON.getJSONArray(rootObject, "items");
                JSONObject newObject = array.getJSONObject(0);
            String userID = newObject.getString("user_id");
            userID = userID.substring(0, userID.length());
            //JsonElement jsonElement1 = parser1.parse(vkapi.getUser(userID));
            JSONObject secondObject = BotGSON.getJSONObject(vkapi.getUser(userID), "response");
            log.log(Level.INFO, "WhoIsUser method called. Returning...");
            return secondObject.getString(wantedInfo).equals(wantedResponse);
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
    }


}
