package com.bol.mancala.api.controller;

import com.bol.mancala.api.dto.PitMoveDTO;
import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.service.GameService;
import com.bol.mancala.api.service.MoveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bol.mancala.api.consts.Constants.STONES_PER_PIT;

@RestController
@RequestMapping(value = "api/v1/mancala")
public class GameResource {

    private final GameService gameService;
    private final MoveService moveService;

    @Autowired
    public GameResource(GameService gameService, MoveService moveService) {
        this.gameService = gameService;
        this.moveService = moveService;
    }

    @PostMapping(value = "/start", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Endpoint to create the game. Receive the players name, and return a Game instance with an id.", produces = "Application/JSON", response = Game.class, httpMethod = "POST")
    public ResponseEntity<Game> startGame(@Valid @RequestBody PlayersDTO playersDTO) {
        Game game = gameService.start(STONES_PER_PIT, playersDTO);
        return new ResponseEntity<>(game, HttpStatus.OK );

    }

    @PutMapping(value = "/move", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Endpoint to move stones from pits. Always returns the game updated.", produces = "Application/JSON", response = Game.class, httpMethod = "POST")
    public ResponseEntity<Game> move(@Valid @RequestBody PitMoveDTO pitMoveDTO) throws Exception {
        Game game = gameService.findGameById(pitMoveDTO.getGameId());
        game = moveService.move(game, pitMoveDTO.getPit());
        game = gameService.update(game);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
}
