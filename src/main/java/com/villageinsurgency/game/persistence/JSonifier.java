package com.villageinsurgency.game.persistence;

import com.villageinsurgency.game.model.Game;
import com.villageinsurgency.game.model.TownCentre;
import com.villageinsurgency.game.model.person.Person;
import com.villageinsurgency.game.model.resourcehotspot.ResourceHotSpot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSonifier {

    public static JSONObject personToJson(Person p) {
        JSONObject savePerson = new JSONObject();

        savePerson.put("isSoldier", p.isSoldier());
        savePerson.put("id", p.getID());
        savePerson.put("gatherRate", p.getGatherRate());
        savePerson.put("nearResource", p.isNearResource());
        savePerson.put("id", p.getID());
        savePerson.put("posX", p.getPos().getPosX());
        savePerson.put("posY", p.getPos().getPosY());
        savePerson.put("attack", p.getAttack());
        savePerson.put("curMaxHealth", p.getCurMaxHealth());
        savePerson.put("health", p.getHealth());

        return savePerson;
    }


    private static JSONArray registryToJson(ArrayList<Person> r) {
        JSONArray saveRegistry = new JSONArray();

        for (Person p : r) {

            saveRegistry.put(personToJson(p));

        }

        return saveRegistry;
    }

    private static JSONObject townToJson(TownCentre t) {

        JSONObject saveTown = new JSONObject();

        saveTown.put("amountFood", t.getAmountFood());
        saveTown.put("amountGold", t.getAmountGold());
        saveTown.put("registry", registryToJson(t.getRegistry()));
        saveTown.put("personID", t.getPersonID());
        saveTown.put("isPlayer", t.isPlayer());

        return saveTown;
    }

    private static JSONObject resourceHotSpotToJson(ResourceHotSpot r) {
        JSONObject saveResourceHotSpot = new JSONObject();

        saveResourceHotSpot.put("resourceRemaining", r.getResourceRemaining());
        return saveResourceHotSpot;
    }

    public static JSONObject gameToJson(Game g) {
        JSONObject saveGame = new JSONObject();
        //TODO: Make Game static? Fix Access
        saveGame.put("turnsPlayed", g.getTurnsPlayed());
        saveGame.put("isPlayerTurn",g.isPlayerTurn());
        saveGame.put("playerTown", townToJson(g.getPlayerTown()));
        saveGame.put("enemyTown", townToJson(g.getEnemyTown()));
        saveGame.put("goldMine", resourceHotSpotToJson(g.goldMine));
        saveGame.put("farm", resourceHotSpotToJson(g.farm));
        return saveGame;
    }
}
