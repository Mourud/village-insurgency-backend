package com.villageinsurgency.game.model.resourcehotspot;

import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.constants.GameConstants;
import org.json.JSONObject;

public class Farm extends ResourceHotSpot {

    private static final Position POSITION = GameConstants.FARM_POSITION;

    // TODO: Add farm finishing

    public Farm() {
        super();
        setPos(POSITION);
    }

    public Farm(JSONObject j) {
        super(j.getInt("resourceRemaining"));
        setPos(POSITION);
    }
}
