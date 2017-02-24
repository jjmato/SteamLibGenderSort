package jjmato.SteamLibGenderSort.SteamApi.pojos;

/**
 * Created by Juan on 24/02/2017.
 */
public class Game {

    private String name;
    private String type;
    private int steam_appid;
    private int appid;
    private String detailed_description;
    private String supported_languages;
    private String header_image;
    private String genderSelectType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSteam_appid() {
        return steam_appid;
    }

    public void setSteam_appid(int steam_appid) {
        this.steam_appid = steam_appid;
    }

    public String getDetailed_description() {
        return detailed_description;
    }

    public void setDetailed_description(String detailed_description) {
        this.detailed_description = detailed_description;
    }

    public String getSupported_languages() {
        return supported_languages;
    }

    public void setSupported_languages(String supported_languages) {
        this.supported_languages = supported_languages;
    }

    public String getHeader_image() {
        return header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getGenderSelectType() {
        return genderSelectType;
    }

    public void setGenderSelectType(String genderSelectType) {
        this.genderSelectType = genderSelectType;
    }
}
