package com.villageinsurgency.game;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.villageinsurgency.game.model.Game;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GameService {
    public static Game createCRUD(Game game) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Pushing the 'game' data to the 'games' node and getting the unique key
        DatabaseReference gamesRef = databaseReference.child("Games").push();
        gamesRef.setValueAsync(game);

        // CompletableFuture is used here to asynchronously handle the response
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete(gamesRef.getKey()); // This returns the unique key of the new child node

        return game;
    }

}
