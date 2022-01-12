package com.bol.mancala.api.service.impl;

import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.Pit;
import com.bol.mancala.api.model.Player;
import com.bol.mancala.api.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.bol.mancala.api.consts.Constants.FIRST_PLAYER;
import static com.bol.mancala.api.consts.Constants.SECOND_PLAYER;

/**
 * Class responsible to do operations in a player.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> choosePlayerStartGame(List<Player> players) {
        Random random = new Random();
        Player player = players.get(random.nextInt(players.size()));
        player.setTurn(true);
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerTurn(List<Player> players, int pitNumber) {
        if (!checkIsPlayerTurn(players, pitNumber)) {
            throw new IllegalArgumentException("Is not your turn");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> changePlayerTurn(Game game) {
        game.getPlayers().forEach(
                player -> player.setTurn(!player.isTurn())
        );
        return game.getPlayers();
    }

    public boolean pitBelongsToPlayer(Game game,Pit pit){
        if(pit.getPosition() < 7 && game.getPlayers().get(FIRST_PLAYER).isTurn()){
            return true;
        }else if(pit.getPosition() > 7 && game.getPlayers().get(SECOND_PLAYER).isTurn()){
            return true;
        }
        return false;
    }

    private boolean checkIsPlayerTurn(List<Player> players, int pitNumber) {
        if (pitNumber < 7) {
            return players.get(FIRST_PLAYER).isTurn();
        }
        return players.get(SECOND_PLAYER).isTurn();
    }




}

