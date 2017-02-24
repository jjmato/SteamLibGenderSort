package jjmato.SteamLibGenderSort.SteamApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import jjmato.SteamLibGenderSort.SteamApi.pojos.Game;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Juan on 17/02/2017.
 */
public class SteamApiService {

    private Properties prop = new Properties();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Type mapStringObjType = new TypeToken<Map<String, Object>>(){}.getType();
    private Type mapStringGameType = new TypeToken<Map<String, Game>>(){}.getType();
    private Type gameType = new TypeToken<List<Game>>(){}.getType();
    private String steamApiKey = null;

    public SteamApiService() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(mapStringObjType, new MapDeserializerDoubleAsIntFix());
        gson = gsonBuilder.create();
        try {
            prop.load(getClass().getResourceAsStream("/config.properties"));
            steamApiKey = prop.getProperty("steam.api.key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, Game> getGamesOfLibrary(String steamUserID) throws UnirestException {
        Map<String, Game> r = new HashMap<String, Game>();
        try {
            r = gson.fromJson(new FileReader("out.json"),mapStringGameType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String steamApiUrlGetLibrary = prop.getProperty("steam.api.UrlGetLibrary");
        String steamApiUrlGetGame = prop.getProperty("steam.api.UrlGetGame");


        HttpRequest httpRequest = buildRouteParam(Unirest.get(steamApiUrlGetLibrary), null, steamUserID, null);

        HttpResponse<String> httpResponse = httpRequest.asString();
        Object foo = httpResponse.getBody();
        Map<String, Object> httpResponseMapped = gson.fromJson(foo.toString(), mapStringObjType);
        Map<String, Object> response2 = new HashMap<String, Object>();
        if (null!= httpResponseMapped.get("response")) {
            response2 = (Map<String, Object>) httpResponseMapped.get("response");
        }
        List<Game> games = gson.fromJson(response2.get("games").toString(), gameType);

        Map<String, Game> finalR = r;
        games.forEach((Game i) -> {

            int appid = i.getAppid();
            String strAppId = String.valueOf(appid);
            System.out.println(appid);

            if (!finalR.isEmpty() && (null != finalR.get(strAppId))){
                System.out.println("game is on cache file");
            }
            else {

                HttpRequest httpRequest1 = buildRouteParam(Unirest.get(steamApiUrlGetGame), null, null, strAppId);
                try {
                    Map<String, Object> httpResponseMapped1 = gson.fromJson(httpRequest1.asString().getBody().toString(), mapStringObjType);
                    if (null != httpResponseMapped1) {
                        Map<String, Object> o1 = (Map<String, Object>) httpResponseMapped1.get(strAppId);
                        if (o1 != null) {
                            Object data = o1.get("data");
                            if (null!=data){
                                JsonElement jsonElement = gson.toJsonTree(data);
                                Game game = gson.fromJson(jsonElement, Game.class);
                                game.setAppid(appid);
                                finalR.put(strAppId, game);
                            }
                        }
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }

            }

        });

        if (!r.isEmpty()){
            updateCacheFile(r);
        }

        return r;
    }

    public void updateCacheFile(Map<String, Game> r) {
        try {
            FileWriter writer = new FileWriter(new File("out.json"));
            gson.toJson(r, writer);
            writer.close();
            System.out.println("write file cache! out.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSteamId(String steamNickName) {

        String r = null;
        String urlGetStreamId = prop.getProperty("steam.api.UrlGetSteamId");

        HttpRequest httpRequest = buildRouteParam(Unirest.get(urlGetStreamId), steamNickName, null, null);
        try {
            Map<String, Object> response = gson.fromJson(httpRequest.asString().getBody(), mapStringObjType);
            Map<String, Object> response1 = (Map<String, Object>) response.get("response");
            r = response1.get("steamid").toString();

        } catch (UnirestException e) {
            e.printStackTrace();
        }


        return r;
    }

    private HttpRequest buildRouteParam(GetRequest getRequest, String steamNickName, String steamUserID, String strAppId) {


        HttpRequest request = getRequest
                .queryString("key", this.steamApiKey)
                .queryString("format", "json");
        if (null!=steamNickName){
            request.queryString("vanityurl",steamNickName);
        }
        if (null!=steamUserID){
            request.queryString("steamid", steamUserID);
        }
        if (null!=strAppId){
            request.queryString("appids", strAppId);
        }
        return request;
    }
}
