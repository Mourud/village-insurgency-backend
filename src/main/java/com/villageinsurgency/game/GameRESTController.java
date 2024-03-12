package com.villageinsurgency.game;

import com.villageinsurgency.game.model.Game;
import com.villageinsurgency.game.model.constants.GameConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class GameRESTController {
    private final Game game = new Game(GameConstants.LEVEL1);

    @PostMapping("/Games")
    public Game postGame() throws InterruptedException, ExecutionException {
            return GameService.createCRUD(game);


    }
}
//    @GetMapping("/makeNewGame")
//    public Game greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Game(counter.incrementAndGet(), String.format(template, name));
//    }
//    int x;
//    @PostMapping("/makePerson")
//    public ResponseEntity<String> makePerson(@RequestBody Json body) {
//        return new ResponseEntity<String>("Hello, " + body.name, HttpStatus.OK);
//    }
//
//    @PostMapping("/employees")
//    Employee newEmployee(@RequestBody Person newPerson) {
//        return repository.save(newEmployee);
//    }
//     make entity
//         make Villagers`
//         make Soldiers
//     move Entity
//         move Villagers
//         move Soldiers



