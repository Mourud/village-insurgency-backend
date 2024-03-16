package com.villageinsurgency.game.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;
import com.villageinsurgency.game.model.Game;
import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.TownCentre;
import com.villageinsurgency.game.model.person.Person;
import com.villageinsurgency.game.parsers.GameParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Person selected = null;
    public int turnLimit;
    boolean gameStart = false;

    private static Game game;


//    private void mouseHandle(MouseEvent e) {
//        Position mousePos = new Position((int) e.getSceneX(), (int) e.getSceneY());
//        TownCentre player = game.getPlayerTown();
//        TownCentre op = game.getEnemyTown();
//        if (game.isPlayerTurn()) {
//            moveOrder(player, op, mousePos);
//        } else {
//            moveOrder(op, player, mousePos);
//        }
//        update();
//    }

    public ResponseEntity<String> createGame(Game game) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Games");
        DatabaseReference newGameRef = ref.push();
        newGameRef.setValueAsync(game);
        String newGameRefKey = newGameRef.getKey();
        return ResponseEntity.ok(newGameRefKey);
    }
    

    public ResponseEntity<String> getGame(String gameKey) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Games");
        ref.child(gameKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object data = dataSnapshot.getValue();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String jsonData = objectMapper.writeValueAsString(data);
                    game = GameParser.parse(jsonData);
                    System.out.println("Oka");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("Error parsing game data");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        newGameRef.setValueAsync(game);
        return ResponseEntity.ok(game.toString());
    }

    public ResponseEntity<String> deleteGame(String gameKey) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Games");
        dbRef.child(gameKey).removeValueAsync();
        return ResponseEntity.ok("Deleted" + gameKey);
    }
    
//    public ResponseEntity<String> testPut(String gameKey) {
//
//        ResponseEntity<String> game = getGame(gameKey);
//        boolean personMadeSuccessful;
//        if (game.isPlayerTurn()) {
//            personMadeSuccessful = game.getPlayerTown().procreateVillager();
//        } else {
//            personMadeSuccessful = game.getEnemyTown().procreateVillager();
//        }
//        if (personMadeSuccessful) {
//            game.setTurnsPlayed(game.getTurnsPlayed() + 2);
//        }
//        update();
//
//    }
    private void moveOrder(TownCentre player, TownCentre op, Position mousePos) {
        if ((selected == (null))) {
            if (player.findPerson(mousePos) != null) {
                selected = player.findPerson(mousePos);
                game.setTurnsPlayed(game.getTurnsPlayed() + 1);
            }
        } else {
            selected.walkTo(mousePos.getPosX(), mousePos.getPosY());
            Person opponent = op.findPerson(mousePos);
            game.setTurnsPlayed(game.getTurnsPlayed() + 1);
            if (opponent != null) {
                selected.attack(opponent);
            }
            selected = null;
        }
    }

    private void update() {
        if (!gameStart) {
            setTurnLimit();
            gameStart = !gameStart;
        }
        if (game.getTurnsPlayed() >= turnLimit) {
            game.setPlayerTurn(!game.isPlayerTurn());
            incrementResources();
            game.setTurnsPlayed(0);
            setTurnLimit();
        }
        game.updateTowns();
//        prepareToUpdatePlayerPosition(TheMythsOfUbc.setScene(render(game)));
        checkGameOver();
    }

    private void incrementResources() {
        if (game.isPlayerTurn()) {
            game.getPlayerTown().incrementResources();
        } else {
            game.getEnemyTown().incrementResources();
        }
    }

    private void setTurnLimit() {
        if (game.isPlayerTurn()) {
            turnLimit = 2 * game.getPlayerTown().getPopSize();
        } else {
            turnLimit = 2 * game.getEnemyTown().getPopSize();
        }
    }

    private void checkGameOver() {
//        if (game.getEnemyTown().getRegistry().isEmpty()) {
////            TheMythsOfUbc.setScene(setGameEnd("BLUE"));
//        } else if (game.getPlayerTown().getRegistry().isEmpty()) {
////            TheMythsOfUbc.setScene(setGameEnd("RED"));
//        }
    }



    public void makeNewGame(String s) {
        game = new Game(s);
        update();
    }




}
