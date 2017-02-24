package jjmato.SteamLibGenderSort.SteamApi.pojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 17/02/2017.
 */
public class ResponsePojo {

    private int game_count;
    private List<Game> games = new ArrayList();

    public int getGame_count() {
        return game_count;
    }

    public void setGame_count(int game_count) {
        this.game_count = game_count;
    }
}
