package com.test.rmk.vkbot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BotActions extends Bot {
    private static Logger log = Logger.getLogger(BotActions.class.getName());

        String getLastHistoryMessage(String LastJSONMessage) throws java.io.IOException {
            try{
            JSONObject rootObject = BotGSON.getJSONObject(LastJSONMessage, "response");
            JSONArray array = BotGSON.getJSONArray(rootObject, "items");
            log.log(Level.INFO, "getLastHistoryMessage method called, returning string with text");
            return array.getString(0);
        } catch(org.json.JSONException ex){
                ex.printStackTrace();
        }
            return "null";
    }


    String getWallNumber(String jsonWall) {
        try {
            JSONObject rootObject = BotGSON.getJSONObject(jsonWall, "response");
            JSONArray array = BotGSON.getJSONArray(rootObject, "items");
            int RandomNumber = (int) (Math.random() * 98);
            JSONObject element = array.getJSONObject(RandomNumber);
            return element.getString("id");
        } catch(Exception ex){
                ex.printStackTrace();
            }
        return "null";
    }

    boolean equalsCitatku(String message) {
        Pattern p = Pattern.compile(("^.*цитатку.*$"), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(message);
        log.log(Level.INFO, "equalsCitatku method called, returning matches");
        return m.matches();
    }

    String getPhotoMemes(String jsonMessage, int max) {
        try {
            JSONObject rootObject = BotGSON.getJSONObject(jsonMessage, "response");
            JSONArray array = BotGSON.getJSONArray(rootObject, "items");
            String randomNumber = VKAPI.invokeApi("https://www.random.org/integers/?num=1&min=1&max="
                    + max + "&col=1&base=10&format=plain&rnd=new");
            JSONObject element = array.getJSONObject(Integer.parseInt(randomNumber) - 1);
            return element.getString("id");
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return "null";
    }

    boolean equalsROK(String message) {
        Pattern p = Pattern.compile(("^.*рок.*$"), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(message);
        log.log(Level.INFO, "equalsROK method called, returning matches");
        return m.matches();
    }
    boolean isLastMessageMem(String lastMessage) {
        Pattern p = Pattern.compile(("^.*мем.*$"), Pattern.UNICODE_CASE |Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(lastMessage);
        log.log(Level.INFO, "isLastMessageMem method called, returning matches");
        return m.matches();
    }
    boolean isLastMessageKM(String lastMessage) {
        Pattern p = Pattern.compile("^.*!КМ.*$");
        Matcher m = p.matcher(lastMessage);
        log.log(Level.INFO, "isLastMessageKM method called, returning matches");
        return m.matches();
    }

    boolean isLastMessageSMILEK(String lastMessage) {
       try {
           log.log(Level.INFO, "isLastMessageSMILEK method called, starting...");
           char[] array = {34, 34, 55357, 56835, 34};
           char[] array2 = {34, 55357, 56835, 34};
           int c = 0;
           if (lastMessage.length() != 5 && lastMessage.length() != 4 && lastMessage.length()!= 2) {
               log.log(Level.INFO, "isLastMessageSMILEK, return false.");
               return false;
           }
           if (lastMessage.length() ==2) {
               if (lastMessage.charAt(0) == 55357 && lastMessage.charAt(1) == 56835) {
                   log.log(Level.INFO, "isLastMessageSMILEK, return true, length = 2");
                   return true;
               }
           }
           if (lastMessage.length() == 5) {
               for (int i = 0; i < 5; i++) {
                   if (lastMessage.charAt(i) == array[i]) {
                       c++;
                   }
               }
               if (c == 5) {
                   log.log(Level.INFO, "isLastMessageSMILEK, return true, length = 5");
                   return true;
               }
           }
           if (lastMessage.length() == 4) {
               c = 0;
               for (int i = 0; i < 5; i++) {
                   if (lastMessage.charAt(i) == array2[i]) {
                       c++;
                   }
               }
               if (c == 4) {
                   log.log(Level.INFO, "isLastMessageSMILEK, return true, length = 4");
                   return true;
               }
           }
       } catch (java.lang.StringIndexOutOfBoundsException ex) {
           log.log(Level.WARNING, "isLastMessageSMILEK, threw Exception: " + ex);
           ex.printStackTrace();
       }
           return false;
    }
}