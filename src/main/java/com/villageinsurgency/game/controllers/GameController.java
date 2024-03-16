package com.villageinsurgency.game.controllers;

import com.villageinsurgency.game.model.Game;
import com.villageinsurgency.game.model.constants.GameConstants;
import com.villageinsurgency.game.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/Games")
    public ResponseEntity<String> postGame() throws InterruptedException, ExecutionException {
        Game game = new Game(GameConstants.LEVEL1);
        return gameService.createGame(game);

    }

    @GetMapping("/Games")
    public ResponseEntity<String> getGame(@RequestParam String gameKey)  throws InterruptedException, ExecutionException{

        return gameService.getGame(gameKey);
    }

    @DeleteMapping("/Games")
    public ResponseEntity<String> deleteGame(@RequestParam String gameKey)  throws InterruptedException, ExecutionException{
        return gameService.deleteGame(gameKey);
    }


//    @PutMapping("/Games")
//    public ResponseEntity<String> putGame(@RequestParam String gameKey)  throws InterruptedException, ExecutionException{
//        return gameService.
//    }
}




