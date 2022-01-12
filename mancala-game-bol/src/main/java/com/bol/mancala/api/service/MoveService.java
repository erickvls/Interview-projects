package com.bol.mancala.api.service;

import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.model.Game;

public interface MoveService {
    /**
     * The method is responsible for moving the game
     * And call all possibles situations of move.
     *
     * @param game      Receive the game to be changed
     * @param pitNumber Receive the pit number that will be pick to collect the stones.
     * @return A game with pits updated.
     * @throws GameException If some exception come up, it will be thrown by GameException showing the issues.
     */
    Game move(Game game, int pitNumber) throws GameException;
}
