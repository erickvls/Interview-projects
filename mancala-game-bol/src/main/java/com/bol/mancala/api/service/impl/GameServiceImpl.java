package com.bol.mancala.api.service.impl;

import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.exceptions.PitNotFoundException;
import com.bol.mancala.api.model.*;
import com.bol.mancala.api.repository.GameRepository;
import com.bol.mancala.api.service.GameService;
import com.bol.mancala.api.service.PlayerMapper;
import com.bol.mancala.api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bol.mancala.api.consts.Constants.*;
import static com.bol.mancala.api.model.StatusGame.IN_PROGRESS;

/**
 * The class represents the service of game.
 * Simple operations like, save, find, update and finish.
 */
@Service
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(PlayerService playerService, PlayerMapper playerMapper, GameRepository gameRepository) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.gameRepository = gameRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game start(int stonesPerPit, PlayersDTO playersDTO) {
        List<Player> players = playerMapper.toModel(playersDTO);
        List<Player> playersWithTurn = playerService.choosePlayerStartGame(players);
        Board board = new Board(stonesPerPit);
        Game game = new Game(board, playersWithTurn, IN_PROGRESS);
        return gameRepository.insert(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game findGameById(String gameId) throws GameException {
        Optional<Game> game = gameRepository.findById(gameId);
        return game.orElseThrow(() -> new GameException("Game not found."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game update(Game game) {
        return gameRepository.save(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game finish(Game game, StatusGame statusGame) throws PitNotFoundException {
        Pit pitStoreLeft = game.getBoard().getPit(PIT_STORE_LEFT);
        Pit pitStoreRight = game.getBoard().getPit(pitStoreLeft.getPosition() - PIT_STORE_RIGHT);
        if (pitStoreLeft.getStones() > pitStoreRight.getStones()) {
            game.setWinner(game.getPlayers().get(SECOND_PLAYER));
        } else {
            game.setWinner(game.getPlayers().get(FIRST_PLAYER));
        }
        game.setStatusGame(statusGame);
        return game;
    }


}
