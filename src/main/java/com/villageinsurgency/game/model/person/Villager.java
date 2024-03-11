package com.villageinsurgency.game.model.person;

import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.TownCentre;
import com.villageinsurgency.game.model.constants.GameConstants;

public class Villager extends Person {

    private static final int SIZE = GameConstants.VILLAGER_SIZE;


    public Villager(int id, int curMaxHealth, int attack, int gatherRate, TownCentre town) {
        super(false, SIZE, id, curMaxHealth, attack, gatherRate, town);
    }

    public Villager(int id, int health, int curMaxHealth, int attack, int gatherRate, Position position,
                    boolean nearResource, TownCentre town) {
        super(false, id, SIZE, health, curMaxHealth, attack, gatherRate, position, nearResource, town);
    }

    @Override
    public boolean isInRange(Position position) {
        return isWithinPos(position, SIZE);
    }
}
