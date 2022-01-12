package com.bol.mancala.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.bol.mancala.api.consts.Constants.PIT_STORE_RIGHT;
import static com.bol.mancala.api.consts.Constants.ZERO;

/**
 * The class represents the game itself.
 * It is composed by id, a board a list of players that will be parsed in two
 * the statusGame {IN_PROGRESS,FINISHED} and with winner.
 */
@Data
@Document
public class Game {
    @Id
    private String id;
    private Board board;
    private List<Player> players;
    private StatusGame statusGame;
    private Player winner;

    /**
     * Creates a game
     *
     * @param board      The board that will represent the game
     * @param players    Players that will be playing.
     * @param statusGame The status of game.
     */
    public Game(Board board, List<Player> players, StatusGame statusGame) {
        this.board = board;
        this.players = players;
        this.statusGame = statusGame;
    }

    /**
     * The method checks if one of sides (Side-bottom or side-top) is empty.
     * That is, there is no ball anymore.
     *
     * @return true if one of two sides are empty, false if not.
     */
    @JsonIgnore
    public boolean isAnySideEmpty() {
        return getTotalStonesBottom() == ZERO || getTotalStonesTop() == ZERO;
    }

    /**
     * The method counts how many stones are in bottom side.
     *
     * @return an amount of stones.
     */
    @JsonIgnore
    public int getTotalStonesBottom() {
        return this.getBoard().getPitList().stream().filter(pit -> !pit.isStore() && pit.getPosition() < PIT_STORE_RIGHT).mapToInt(Pit::getStones).sum();
    }

    /**
     * The method counts how many stones are in top side.
     *
     * @return an amount of stones.
     */
    @JsonIgnore
    public int getTotalStonesTop() {
        return this.getBoard().getPitList().stream().filter(pit -> !pit.isStore() && pit.getPosition() > PIT_STORE_RIGHT).mapToInt(Pit::getStones).sum();
    }

    /**
     * The method clear all stones on the board
     * filter when a pit is Store and a pit has balls
     * It is used when game has finished. It needs to clear all balls on the board.
     */
    @JsonIgnore
    public void clearSide() {
        this.getBoard().getPitList().stream().filter(pit -> !pit.isStore() && !pit.isEmpty()).forEach(Pit::clearStone);
    }

    /**
     * The method checks status game.
     *
     * @return true if game is over, false if not.
     */
    @JsonIgnore
    public boolean isGameOver() {
        return statusGame.equals(StatusGame.FINISHED);
    }


}
