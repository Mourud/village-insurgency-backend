package com.villageinsurgency.game.controllers;

import com.villageinsurgency.game.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PutMapping("/units/create")
    public ResponseEntity<String> createUnit(@RequestParam int gameKey, @RequestParam String type, @RequestParam String user) {
        return gameService.createUnit(gameKey, type, user);
    }

    @PostMapping("/games/create")
    public ResponseEntity<String> createNewGame(@RequestParam String p1) {
        return gameService.createGame(p1);
    }

    @PutMapping("/games/join")
    public ResponseEntity<String> joinGame(@RequestParam int gameKey, @RequestParam String p2){
        return gameService.joinGame(gameKey, p2);
    }

//    @GetMapping("/Games")
//    public ResponseEntity<String> getGame(@RequestParam int gameKey) throws InterruptedException, ExecutionException {
//        String gameJson = gameService.getGameObjectJsonById(gameKey);
//        return ResponseEntity.ok(gameJson);
//    }



}




