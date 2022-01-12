package com.bol.mancala.api.service;

import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.exceptions.PitNotFoundException;
import com.bol.mancala.api.model.Board;
import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.Player;
import com.bol.mancala.api.model.StatusGame;
import com.bol.mancala.api.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;


    @Test
    public void shouldCreateANewGameInstanceWhenGameStarted() throws PitNotFoundException {
        PlayersDTO playersDTO = new PlayersDTO("Erick","Alex");
        Game game  = gameService.start(6,playersDTO);
        assertEquals(game.getBoard().getPitList().size(),14);
        assertEquals(game.getPlayers().size(),2);
        assertEquals(game.getBoard().getPit(1).getStones(),6);
        assertEquals(game.getBoard().getPit(2).getStones(),6);
        assertEquals(game.getBoard().getPit(3).getStones(),6);
        assertEquals(game.getBoard().getPit(4).getStones(),6);
        assertEquals(game.getBoard().getPit(5).getStones(),6);
        assertEquals(game.getBoard().getPit(6).getStones(),6);
        assertEquals(game.getBoard().getPit(7).getStones(),0);
        assertEquals(game.getBoard().getPit(8).getStones(),6);
        assertEquals(game.getBoard().getPit(9).getStones(),6);
        assertEquals(game.getBoard().getPit(10).getStones(),6);
        assertEquals(game.getBoard().getPit(11).getStones(),6);
        assertEquals(game.getBoard().getPit(12).getStones(),6);
        assertEquals(game.getBoard().getPit(13).getStones(),6);
        assertEquals(game.getBoard().getPit(14).getStones(),0);

        assertTrue(game.getBoard().getPit(7).isStore());
        assertTrue(game.getBoard().getPit(14).isStore());

        assertEquals(game.getStatusGame(),StatusGame.IN_PROGRESS);

    }

    @Test
    public void shouldLoadGameFromInstance () throws GameException, PitNotFoundException {
        Board board = new Board(6);
        List<Player> players = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        Game expectedGame = new Game(board , players, StatusGame.IN_PROGRESS);
        expectedGame.setId("6135d3718e291970d3401161");

        Optional<Game> gameOptional= Optional.of(expectedGame);
        Mockito.when(gameRepository.findById("6135d3718e291970d3401161")).thenReturn(gameOptional);

        Game game = gameService.findGameById("6135d3718e291970d3401161");

        assertEquals(game.getId(),"6135d3718e291970d3401161");
        assertEquals(game.getBoard().getPitList().size(),14);
        assertEquals(game.getPlayers().size(),2);
        assertEquals(game.getBoard().getPit(1).getStones(),6);
        assertEquals(game.getBoard().getPit(2).getStones(),6);
        assertEquals(game.getBoard().getPit(3).getStones(),6);
        assertEquals(game.getBoard().getPit(4).getStones(),6);
        assertEquals(game.getBoard().getPit(5).getStones(),6);
        assertEquals(game.getBoard().getPit(6).getStones(),6);
        assertEquals(game.getBoard().getPit(7).getStones(),0);
        assertEquals(game.getBoard().getPit(8).getStones(),6);
        assertEquals(game.getBoard().getPit(9).getStones(),6);
        assertEquals(game.getBoard().getPit(10).getStones(),6);
        assertEquals(game.getBoard().getPit(11).getStones(),6);
        assertEquals(game.getBoard().getPit(12).getStones(),6);
        assertEquals(game.getBoard().getPit(13).getStones(),6);
        assertEquals(game.getBoard().getPit(14).getStones(),0);

        assertTrue(game.getBoard().getPit(7).isStore());
        assertTrue(game.getBoard().getPit(14).isStore());

        assertEquals(game.getStatusGame(),StatusGame.IN_PROGRESS);
    }

    @Test(expected = GameException.class)
    public void shouldThrowAnExceptionWhenGameIsNotFound () throws GameException {
        Board board = new Board(6);
        List<Player> players = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        Game expectedGame = new Game(board , players, StatusGame.IN_PROGRESS);
        expectedGame.setId("123412");

        Optional<Game> gameOptional= Optional.of(expectedGame);
        Game game = gameService.findGameById("123412");
    }

    @Test
    public void shouldChangeStatusGameWhenGameIsFinished () throws  PitNotFoundException {
        Board board = new Board(6);
        List<Player> players = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        Game game = new Game(board , players, StatusGame.FINISHED);
        board.getPit(1).setStones(0);
        board.getPit(2).setStones(0);
        board.getPit(3).setStones(0);
        board.getPit(4).setStones(0);
        board.getPit(5).setStones(0);
        board.getPit(6).setStones(0);
        board.getPit(7).setStones(5);
        board.getPit(8).setStones(5);
        board.getPit(9).setStones(3);
        board.getPit(10).setStones(0);
        board.getPit(11).setStones(0);
        board.getPit(12).setStones(0);
        board.getPit(13).setStones(0);
        board.getPit(14).setStones(6);


        Game gameResult = gameService.finish(game,StatusGame.FINISHED);
        assertTrue(game.isAnySideEmpty());
        assertEquals(gameResult.getStatusGame(),game.getStatusGame());
        assertEquals(game.getWinner().getName(),gameResult.getWinner().getName());
    }
}
