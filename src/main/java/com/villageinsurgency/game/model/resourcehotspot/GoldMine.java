package com.villageinsurgency.game.model.resourcehotspot;

import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.constants.GameConstants;
import org.json.JSONObject;

public class GoldMine extends ResourceHotSpot {



    private static final Position POSITION = GameConstants.GOLD_MINE_POSITION;


    public GoldMine() {
        super();
        setPos(POSITION);
    }

    public GoldMine(JSONObject j) {
        super(j.getInt("resourceRemaining"));
        setPos(POSITION);
    }

//    public void depleted(){
//        setFill(GOLDMINE_DEAD_COLOR);
//    }


//todo: add dying function


//    @Override
//    public boolean decrementResourceLeft(int gatherRate) {
//        if(super.decrementResourceLeft(gatherRate))
//
//        return super.decrementResourceLeft(gatherRate);
//    }
}
