package com.villageinsurgency.game.model.person;

import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.TownCentre;
import com.villageinsurgency.game.model.constants.GameConstants;

public class Soldier extends Person {
    private static final int SIZE = GameConstants.SOLDIER_SIZE;


    // MODIFIES: this
    // EFFECTS: SETS GATHER RATE TO 50 BY DEFAULT
    public Soldier(int id, int curMaxHealth, int attack, int gatherRate, TownCentre town) {
        super(true, SIZE, id, curMaxHealth, attack, gatherRate, town);
    }

    public Soldier(int id, int health, int curMaxHealth, int attack, int gatherRate, Position position,
                   boolean nearResource, TownCentre town) {
        super(true, id, SIZE, health, curMaxHealth, attack, gatherRate, position, nearResource, town);
    }

    @Override
    public boolean isInRange(Position position) {
        return isWithinPos(position, SIZE);
    }
}
