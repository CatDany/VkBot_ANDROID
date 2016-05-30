package com.test.rmk.vkbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that helps to communicate with VKAPI.
 */
class VKAPI {
        private static final String API_VERSION = "5.21";
        private static final String AUTH_URL = "https://oauth.vk.com/authorize"
                + "?client_id={APP_ID}"
                + "&scope={PERMISSIONS}"
                + "&redirect_uri={REDIRECT_URI}"
                + "&display={DISPLAY}"
                + "&v={API_VERSION}"
                + "&response_type=token";
        private static final String API_REQUEST = "https://api.vk.com/method/{METHOD_NAME}"
                + "?{PARAMETERS}"
                + "&access_token={ACCESS_TOKEN}"
                + "&v=" + API_VERSION;
    private static final String API_REQUEST_NO_TOKEN = "https://api.vk.com/method/{METHOD_NAME}"
            + "?{PARAMETERS}"
            + "&v=" + API_VERSION;
    static private final int SLEEPTIME = 120000;
    private static Logger log = Logger.getLogger(VKAPI.class.getName());
    private final String accessToken;

    /**
     * Standart constructr, mostly used in VKAPI.auth
     *
     * @param appId       ID of app that you creating
     * @param accessToken accessToken for your app.
     * @throws IOException
     */
    VKAPI(final String appId, final String accessToken) throws IOException {
            this.accessToken = accessToken;
    }

    /**
     * Method that invokes an url and returns String that he read.
     *
     * @param requestUrl URL text from which will be read.
     * @return String with everything that was read from URL.
     */
    static String invokeApi(final String requestUrl) {
        final StringBuilder result = new StringBuilder();
        try {
            final URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            //reader.lines().forEach(result::append);
            reader.close();
            is.close();
        } catch (Exception ex) {
            try {
                Thread.sleep(SLEEPTIME);
                final URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while(reader.ready()){
                    result.append(reader.readLine());
                }
                reader.close();
                is.close();
                return result.toString();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            ex.printStackTrace();
            log.log(Level.INFO, "invokeApi used, result: " + result.toString());

        }
        return result.toString();
    }

    /**
     * Authentificate your application and gets an accesstoken.
     *
     * @param appId ID of app that you creating
     * @throws IOException
     */
    /*static private void auth(final String appId) throws IOException {
         String reqUrl = AUTH_URL
                    .replace("{APP_ID}", appId)
                    .replace("{PERMISSIONS}", "photos,messages")
                    .replace("{REDIRECT_URI}", "https://oauth.vk.com/blank.html")
                    .replace("{DISPLAY}", "page")
                    .replace("{API_VERSION}", API_VERSION);
            try {
                Desktop.getDesktop().browse(new URL(reqUrl).toURI());
            } catch (URISyntaxException ex) {
                throw new IOException(ex);
            }
    }*/

    /**
     * Get a dialogs from vk.
     *
     * @return JSON string with dialogs.
     * @throws IOException
     */

    /**
     * Get history of messages in VK.
     *
     * @param userId id of user with whom you had a dialog.
     * @param offset offset of returning message(-s).
     * @param count  amount of strings to be returned.
     * @param rev    lol, just keep it false or check vkapi doc.
     * @return JSON strings with output.
     * @throws IOException when can't read used to throw IOEXCEPTION
     */
    String getHistory(final String userId, final int offset, final int count, final boolean rev) throws IOException {

            String reqURL = API_REQUEST
                    .replace("{METHOD_NAME}", "messages.getHistory")
                    .replace("{PARAMETERS}", "offset=" + offset + "&" +
                            "count=" + count + "&" + "user_id=" + userId)
                    .replace("{ACCESS_TOKEN}", accessToken);
        return invokeApi(reqURL);
    }

    /**
     * Returns an albums of user.
     *
     * @param userId id of user from whom you want to get albums.
     * @return String with JSON.
     * @throws IOException
     */

    /**
     * Get a photos from vk using vkapi.
     *
     * @param ownerID photo-owner id
     * @param albumID album of photos id
     * @param count   amount of photos to be returned
     * @return JSON strings
     * @throws IOException
     */
    String getPhotos(final String ownerID, final String albumID, final int count) throws IOException {
        String reqURL = API_REQUEST_NO_TOKEN
                .replace("{METHOD_NAME}", "photos.get")
                .replace("{PARAMETERS}", "owner_id=" + ownerID + "&" + "album_id=" + albumID +
                        "&rev=0&extended=0&photo_sizes=0&" + "count=" + count);
        return invokeApi(reqURL);
    }

    /**
     *
     * @param messageReceiver "user" or "group"
     * @param receiverID id of group or user who will receive a message
     * @param message text that you want to send
     * @return JSON Strings
     * @throws IOException
     */
    String sendMessage(String messageReceiver, final String receiverID, final String message) throws IOException {
        if (messageReceiver.equals("user")) {
                messageReceiver = "user_id=";
        } else if (messageReceiver.equals("group")) {
                messageReceiver = "chat_id=";
            }
            String reqUrl = API_REQUEST
                    .replace("{METHOD_NAME}", "messages.send")
                    .replace("{PARAMETERS}", messageReceiver + receiverID + "&" + "message=" + URLEncoder.encode(message))
                    .replace("{ACCESS_TOKEN}", accessToken);
        return invokeApi(reqUrl);
    }

    /**
     * @param messageReceiver a person who is receiving a message, "group" or "user"
     * @param receiverID      id of a person receiving a message.
     * @param message         message that you are sending
     * @param stickerID       id of sticker that will be added
     * @return JSON Strings
     * @throws IOException
     */
    String sendMessageWithSticker(String messageReceiver, final String receiverID, final String message, final String stickerID) throws IOException {
        if (messageReceiver.equals("user")) {
            messageReceiver = "user_id=";
        } else if (messageReceiver.equals("group")) {
            messageReceiver = "chat_id=";
        }
        String reqUrl = API_REQUEST
                .replace("{METHOD_NAME}", "messages.send")
                .replace("{PARAMETERS}", messageReceiver + receiverID + "&" + "message=" + URLEncoder.encode(message) + "&sticker_id=" + stickerID)
                .replace("{ACCESS_TOKEN}", accessToken);
        return invokeApi(reqUrl);
    }

    String getWeather(String city) {
        String WeatherAPI = "api.openweathermap.org/data/2.5/forecast?q=Omsk,us&mode=json&APPID=dcef49ed29cebb5e3f4ad008236f367e"
                .replace("Omsk", city);
        return (invokeApi(WeatherAPI));
    }

    String getWall(String owner_id, String count) { //-119328367
        String reqUrl = API_REQUEST
                .replace("{METHOD_NAME}", "wall.get")
                .replace("{PARAMETERS}", "owner_id=" + owner_id + "&" + "count=" + count)
                .replace("{ACCESS_TOKEN}", accessToken);
        return invokeApi(reqUrl);
    }

    String sendMessage(String messageReceiver, String receiverID, String message, String attachment) throws IOException {
        if (messageReceiver.equals("user")) {
            messageReceiver = "user_id=";
        } else if (messageReceiver.equals("group")) {
            messageReceiver = "chat_id=";
        }
        //https://api.vk.com/method/messages.send?user=224005125&message=message&attachment=photo-73598440_372211073"&access_token={ACCESS_TOKEN}"&v=" + API_VERSION;
        String reqUrl = API_REQUEST
                .replace("{METHOD_NAME}", "messages.send")                                                            //photo-73598440_372211073
                .replace("{PARAMETERS}", messageReceiver + receiverID + "&" + "message=" + URLEncoder.encode(message) + "&" + "attachment=" + attachment + "&notification=0")
                .replace("{ACCESS_TOKEN}", accessToken);
        return invokeApi(reqUrl);
    }

         String getUser(String user_id) throws IOException{
            String reqUrl = API_REQUEST
                    .replace("{METHOD_NAME}", "users.get")
                    .replace("{PARAMETERS}", "user_ids=" + user_id)
                    .replace("&access_token={ACCESS_TOKEN}", "");
            return invokeApi(reqUrl);
        }

}
