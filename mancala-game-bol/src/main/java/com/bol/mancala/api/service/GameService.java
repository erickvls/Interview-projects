package com.bol.mancala.api.service;

import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.exceptions.PitNotFoundException;
import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.StatusGame;

public interface GameService {

    /**
     * The method starts a game, and save a game in a database.
     *
     * @param stonesPerPit How many stones each pit will have.
     * @param playersDTO   Players that will be playing the game.
     * @return An updated game.
     */
    Game start(int stonesPerPit, PlayersDTO playersDTO);

    /**
     * The method find a game by id.
     *
     * @param gameId Game's id.
     * @return Returns a game.
     * @throws GameException If game was not found
     */
    Game findGameById(String gameId) throws GameException;

    /**
     * The method update a game and save in a database.
     *
     * @param game Receive a game that needs to be updated.
     * @return An updated game.
     */
    Game update(Game game);

    /**
     * The method finish the game.
     *
     * @param game       Receive a current game
     * @param statusGame Receive a status of game.
     * @return An game updated
     * @throws PitNotFoundException if Pit has not been found.
     */
    Game finish(Game game, StatusGame statusGame) throws PitNotFoundException;
}
