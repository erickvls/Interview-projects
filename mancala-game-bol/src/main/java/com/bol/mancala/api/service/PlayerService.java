package com.bol.mancala.api.service;

import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.Pit;
import com.bol.mancala.api.model.Player;

import java.util.List;

public interface PlayerService {
    /**
     * The method draws from a list of two players which one will start
     *
     * @param players List of players that will be drawn
     * @return A list of players where one of them has a flag isTurn true
     */
    List<Player> choosePlayerStartGame(List<Player> players);

    /**
     * Checks from a list of players if is a turn from a right player, passing its pit number.
     *
     * @param players   List of players
     * @param pitNumber Pit number that player pick.
     * @return true if pit number match with player's turn, false if not.
     */
    boolean isPlayerTurn(List<Player> players, int pitNumber);

    /**
     * It will change the player's turn
     *
     * @param game Receive a game which will fetch players and switch the turn.
     * @return A list of players with turn changed.
     */
    List<Player> changePlayerTurn(Game game);

    /**
     * Check if a pit belongs to a player receiving a pit and checking the player postion
     * through the game.
     * @param game The game that needs to be checked
     * @param pit Current pit
     * @return true, if a pit belongs to a current player, false if not.
     */
    boolean pitBelongsToPlayer(Game game,Pit pit);
}
