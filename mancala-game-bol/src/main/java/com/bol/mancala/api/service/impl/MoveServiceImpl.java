package com.bol.mancala.api.service.impl;

import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.exceptions.PitNotFoundException;
import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.Pit;
import com.bol.mancala.api.model.Player;
import com.bol.mancala.api.model.StatusGame;
import com.bol.mancala.api.service.GameService;
import com.bol.mancala.api.service.MoveService;
import com.bol.mancala.api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bol.mancala.api.consts.Constants.*;

/**
 * Class responsible for handling the game.
 */
@Service
public class MoveServiceImpl implements MoveService {

    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public MoveServiceImpl(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    /**
     * {@inheritDoc}
     * Checks if game is over.
     * Change the turn if last ball drops into its pit store.
     */
    @Override
    public Game move(Game game, int pitNumber) throws GameException {
        try {
            if (game.isGameOver()) {
                throw new GameException("Game has finished");
            }
            checkMovementAllowed(pitNumber);
            playerService.isPlayerTurn(game.getPlayers(), pitNumber);
            Pit pitSelected = game.getBoard().getPit(pitNumber);
            int stonesPitSelected = pitSelected.getStones();
            pitSelected.clearStone();
            Pit nextPit = sow(game, stonesPitSelected, pitSelected);

            lastBallInAnEmptyPit(game, nextPit);

            // change turn if is not in PitStore.
            if (nextPit.getPosition() != PIT_STORE_LEFT && game.getPlayers().get(SECOND_PLAYER).isTurn()
                    || nextPit.getPosition() != PIT_STORE_RIGHT && game.getPlayers().get(FIRST_PLAYER).isTurn()) {
                playerService.changePlayerTurn(game);
            }

            if (game.isAnySideEmpty()) {
                sowStore(game);
                gameService.finish(game, StatusGame.FINISHED);
            }
        } catch (PitNotFoundException | IllegalArgumentException e) {
            throw new GameException(e.getMessage());
        }
        return game;
    }

    /**
     * The method is responsible to get the pit selected and sow the next pits, according to the amount of stones.
     * If the pit is a PitStore, so jump one position and take another pit.
     *
     * @param game              Receive the game to be processed.
     * @param stonesPitSelected Amount of stones from a pit that has been selected.
     * @param pit               The pit that has been selected.
     * @return The next pit.
     * @throws PitNotFoundException If Pit doesn't exists.
     */
    private Pit sow(Game game, int stonesPitSelected, Pit pit) throws PitNotFoundException {
        if (stonesPitSelected == ZERO) {
            return pit;
        }
        if (pit.getNextPit() == PIT_STORE_LEFT && game.getPlayers().get(FIRST_PLAYER).isTurn()
                || pit.getNextPit() == PIT_STORE_RIGHT && game.getPlayers().get(SECOND_PLAYER).isTurn()) {
            pit = game.getBoard().getPit(pit.getNextPit());
        }
        pit = game.getBoard().getPit(pit.getNextPit());
        pit.addStone();
        return sow(game, --stonesPitSelected, pit);
    }

    /**
     * The method is responsible to sow the store when game has any empty side.
     * That is, if some player's side there is no stones anymore.
     *
     * @param game Receive the game
     * @throws PitNotFoundException If Pit doesn't exists.
     */
    private void sowStore(Game game) throws PitNotFoundException {
        Pit pitStoreRight = game.getBoard().getPit(PIT_STORE_RIGHT);
        pitStoreRight.addStones(game.getTotalStonesBottom());
        Pit pitStoreLeft = game.getBoard().getPit(PIT_STORE_LEFT);
        pitStoreLeft.addStones(game.getTotalStonesTop());
        game.clearSide();
    }

    /**
     * The method will check if is the last ball in a Pit empty.
     * That is, after sow all pits, if the last ball drops in a empty pit and is not a StorePit,
     * So check the opponent pit.
     * If is player's turn and opponent pit is not empty, so catch all stones from opponent pit,
     * Sum with last stone just added in own pit and drop all into its store.
     * After that, clear opposite pits and own pit.
     *
     * @param game    Receive the game
     * @param nextPit The pit that will be sown.
     * @throws PitNotFoundException If Pit doesn't exists.
     */
    private void lastBallInAnEmptyPit(Game game, Pit nextPit) throws PitNotFoundException {
        if (nextPit.getStones() == 1 && !nextPit.isStore()) {
            Pit opponentPit = game.getBoard().getPit(TOTAL_PITS - nextPit.getPosition());
            boolean pitBelongsToPlayer = playerService.pitBelongsToPlayer(game,nextPit);
            if (pitBelongsToPlayer && !opponentPit.isEmpty()) {
                int pitStorePosition = nextPit.getPosition() < PIT_STORE_RIGHT ? PIT_STORE_RIGHT : PIT_STORE_LEFT;
                Pit pitStore = game.getBoard().getPit(pitStorePosition);
                pitStore.addStones(opponentPit.getStones() + nextPit.getStones());
                nextPit.clearStone();
                opponentPit.clearStone();
            }
        }
    }

    /**
     * The method will check if movement is allowed.
     * If get a invalid pit number will throw an exception. If pit number is valid, keep going.
     *
     * @param pitNumber Pit number.
     */
    private void checkMovementAllowed(int pitNumber) {
        if (pitNumber == PIT_STORE_RIGHT || pitNumber == PIT_STORE_LEFT || pitNumber > PIT_STORE_LEFT || pitNumber < ZERO) {
            throw new IllegalArgumentException("Movement not allowed");
        }
    }

}
