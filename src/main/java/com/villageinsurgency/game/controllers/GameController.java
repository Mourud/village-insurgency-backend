package com.villageinsurgency.game.controllers;

import com.villageinsurgency.game.model.Game;
import com.villageinsurgency.game.model.Position;
import com.villageinsurgency.game.model.TownCentre;
import com.villageinsurgency.game.model.person.Person;
import com.villageinsurgency.game.parsers.GameParser;
import com.villageinsurgency.game.persistence.JSonifier;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameController {


    private Person selected = null;
    public int turnLimit;
    boolean gameStart = false;

    private static Game game;

    protected void save(Game g) {
        JSONObject savedGame = JSonifier.gameToJson(g);
        Path path = Paths.get("savedGame.json");
        try {
            Files.write(path, savedGame.toString().getBytes());
        } catch (IOException e) {
           System.out.println("Error saving game");
        }
    }

    protected void load() {
        Path path = Paths.get("savedGame.json");
        String s;
        try (Stream<String> stream = Files.lines(path)) {
            s = stream.collect(Collectors.joining("\n"));
            game = GameParser.parse(s);
        } catch (IOException e) {
            System.out.println("No saved games found");
        }
    }

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
