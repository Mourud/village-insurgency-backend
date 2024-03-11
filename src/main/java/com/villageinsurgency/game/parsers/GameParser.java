package com.villageinsurgency.game.parsers;

import com.villageinsurgency.game.model.Game;
import org.json.JSONObject;

public class GameParser {


    public static Game parse(String input) {
        JSONObject savedGame = new JSONObject(input);
        return new Game(savedGame);
    }


}
