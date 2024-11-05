package com.villageinsurgency.game.model;

import com.villageinsurgency.game.model.constants.GameConstants;
import com.villageinsurgency.game.model.person.Person;
import com.villageinsurgency.game.model.person.Soldier;
import com.villageinsurgency.game.model.person.Villager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TownCentre extends GameObject {

    // CONSTANTS

    // TOWN CENTER
    private static final int SIZE = GameConstants.TOWN_SIZE;

    // VILLAGER STATS
    private int villagerMaxHealth = GameConstants.STARTING_MAX_HEALTH_VILLAGER;
    private int villagerAttack = GameConstants.STARTING_ATTACK_VILLAGER;
    private int villagerGatherRate = GameConstants.STARTING_GATHER_RATHER_VILLAGER;
    private int villagerFoodPrice = GameConstants.STARTING_VILLAGER_FOOD_PRICE;
    private int villagerGoldPrice = GameConstants.STARTING_VILLAGER_GOLD_PRICE;

    // SOLDIER STATS
    private int soldierMaxHealth = GameConstants.STARTING_MAX_HEALTH_SOLDIER;
    private int soldierAttack = GameConstants.STARTING_ATTACK_SOLDIER;
    private int soldierGatherRate = GameConstants.STARTING_GATHER_RATHER_SOLDIER;
    private int soldierFoodPrice = GameConstants.STARTING_SOLDIER_FOOD_PRICE;
    private int soldierGoldPrice = GameConstants.STARTING_SOLDIER_GOLD_PRICE;

    // FIELDS
    private int amountFood;
    private int amountGold;
    private ArrayList<Person> registry;
    private int personID = 0;
    private boolean isPlayer;

    public boolean isPlayer() {
        return isPlayer;
    }
// REQUIRES: (startPop, startFood, startGold) > 0
    // MODIFIES: this
    // EFFECTS: constructs TownCentre with
    //  startFood amount of food,
    //  startGold amount of gold,
    //  startPop amount of Villagers

    public TownCentre(int startPop, int startFood, int startGold, boolean isPlayer) {
        super(SIZE, SIZE);
        this.isPlayer = isPlayer;
        if (isPlayer) {

            setPos(GameConstants.PLAYER_TOWN_POS);
        } else {

            setPos(GameConstants.ENEMY_TOWN_POS);
        }
//        registry = new Registry();
        registry = new ArrayList<Person>();
        for (int i = 0; i < startPop; i++) {
            personID++;
            registry.add(new Villager(
                    personID,
                    villagerMaxHealth,
                    villagerAttack,
                    villagerGatherRate,
                    this
            ));
        }
        amountFood = startFood;
        amountGold = startGold;
    }


    public TownCentre(JSONObject j) {
        super(SIZE, SIZE);
        isPlayer = j.getBoolean("isPlayer");
        amountFood = j.getInt("amountFood");
        amountGold = j.getInt("amountGold");
        personID = j.getInt("personID");
        JSONArray registryJson = j.getJSONArray("registry");
        registry = new ArrayList<>();
        for (Object object : registryJson) {
            JSONObject personJson = (JSONObject) object;
            int idJson = personJson.getInt("id");
            int healthJson = personJson.getInt("health");
            int curMaxHealthJson = personJson.getInt("curMaxHealth");
            int attackJson = personJson.getInt("attack");
            int gatherRateJson = personJson.getInt("gatherRate");
//            JSONObject position = personJson.getJSONObject("pos");
            int posXJson = personJson.getInt("posX");
            int posYJson = personJson.getInt("posY");
            Position positionJson = new Position(posXJson, posYJson);
            boolean nearResourceJson = personJson.getBoolean("nearResource");
            boolean isSoldierJson = personJson.getBoolean("isSoldier");
            if (!isSoldierJson) {
                registry.add(
                        new Villager(idJson,
                                healthJson,
                                curMaxHealthJson,
                                attackJson,
                                gatherRateJson,
                                positionJson,
                                nearResourceJson,
                                this));
            } else {
                registry.add(
                        new Soldier(idJson,
                                healthJson,
                                curMaxHealthJson,
                                attackJson,
                                gatherRateJson,
                                positionJson,
                                nearResourceJson,
                                this));
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: 1. adds new villager to the registry
    //          2. reduces amountFood by villagerFoodPrices
    public boolean procreateVillager() /*throws NegativeResourceException */ {
        if (amountFood >= villagerFoodPrice && amountGold >= villagerGoldPrice) {
            personID++;
            registry.add(new Villager(
                    personID,
                    villagerMaxHealth,
                    villagerAttack,
                    villagerGatherRate,
                    this
            ));
            amountFood -= villagerFoodPrice;
            amountGold -= villagerGoldPrice;
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: 1. adds new soldier to the registry
    //          2. reduces amountFood by soldierFoodPrices
    public boolean procreateSoldier() {
        if (amountFood >= soldierFoodPrice && amountGold >= soldierGoldPrice) {
            personID++;
            registry.add(new Soldier(
                    personID,
                    soldierMaxHealth,
                    soldierAttack,
                    soldierGatherRate,
                    this));
            amountFood -= soldierFoodPrice;
            amountGold -= soldierGoldPrice;
            return true;
        } else {
            return false;
        }
    }

    public Person findPerson(Position position) {
        for (Person person : registry) {
            if (person.isInRange(position)) {
                return person;
            }

        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds resource to the current resourceAmount
    //          reduces resources in ResourceHotSpot
    public void gatherResource(String resourceName, int gatherRate) {
        if (gatherRate != 0) {
            if (resourceName.equals("G")) {
                if (Game.goldMine.decrementResourceLeft(gatherRate)) {
                    amountGold += gatherRate;
                }
            } else if (resourceName.equals("F")) {
                if (Game.farm.decrementResourceLeft(gatherRate)) {
                    amountFood += gatherRate;
                }
            } else {
                System.out.println("INVALID");
            }
        } else {
            System.out.println("Can't gather");
        }
    }

    public int getAmountFood() {
        return amountFood;
    }

    public void setAmountFood(int amountFood) {
        this.amountFood = amountFood;
    }

    public void setAmountGold(int amountGold) {
        this.amountGold = amountGold;
    }

    public int getAmountGold() {
        return amountGold;
    }

    public ArrayList<Person> getRegistry() {
        return registry;
    }

    public int getPopSize() {
        return registry.size();
    }

    public int getPersonID() {
        return personID;
    }

    public void incrementResources() {

        for (Person p : registry) {
            int zone = p.getPersonGameZone();
            switch (zone) {
                case GameConstants.IS_IN_FARM_CODE:
                    p.gatherResource("F");
                    break;
                case GameConstants.IS_IN_GOLD_MINE_CODE:
                    p.gatherResource("G");
                    break;
                default:
                    doNothing();

            }
        }
    }

    private void doNothing() {
    }

    public void update() {
        for (Person p : registry) {
            p.setPos();
        }
    }
}