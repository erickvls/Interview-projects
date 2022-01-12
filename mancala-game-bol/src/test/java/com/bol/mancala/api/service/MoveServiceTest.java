package com.bol.mancala.api.service;

import com.bol.mancala.api.exceptions.GameException;
import com.bol.mancala.api.model.Board;
import com.bol.mancala.api.model.Game;
import com.bol.mancala.api.model.Player;
import com.bol.mancala.api.model.StatusGame;
import com.bol.mancala.api.service.impl.MoveServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MoveServiceTest {

    private static final int stonesPerPit = 6;

    private Game game;

    @InjectMocks
    private MoveServiceImpl moveService;

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    private Board board;

    private List<Player> players;



    @Before
    public void setupTest (){
        board = new Board(stonesPerPit);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSowOfSecondPitPlayerBottom() throws GameException {
        players = Arrays.asList(new Player("Erick",true),new Player("Alex",false));
        List<Player> playersChanged = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        this.game = new Game(board,players, StatusGame.IN_PROGRESS);
        when(playerService.isPlayerTurn(players,2)).thenReturn(true);
        when(playerService.changePlayerTurn(game)).thenReturn(playersChanged);
        Game result = moveService.move(this.game, 2);
        game.setPlayers(playersChanged);
        assertEquals(result.getBoard().getPitList().toString(),"[1:6, 2:0, 3:7, 4:7, 5:7, 6:7, 7:1, 8:7, 9:6, 10:6, 11:6, 12:6, 13:6, 14:0]");
        assertTrue(game.getPlayers().get(1).isTurn());
    }

    @Test
    public void shouldSowOfSecondPitPlayerTop() throws GameException {
        players = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        List<Player> playersChanged = Arrays.asList(new Player("Erick",true),new Player("Alex",false));
        this.game = new Game(board,players, StatusGame.IN_PROGRESS);
        when(playerService.isPlayerTurn(players,9)).thenReturn(true);
        when(playerService.changePlayerTurn(game)).thenReturn(playersChanged);
        Game result = moveService.move(this.game, 9);
        game.setPlayers(playersChanged);
        assertEquals(result.getBoard().getPitList().toString(),"[1:7, 2:6, 3:6, 4:6, 5:6, 6:6, 7:0, 8:6, 9:0, 10:7, 11:7, 12:7, 13:7, 14:1]");
        assertTrue(game.getPlayers().get(0).isTurn());
    }

    @Test
    public void shouldSowOfSixthPitPlayerBottom() throws GameException {
        players = Arrays.asList(new Player("Erick",true),new Player("Alex",false));
        List<Player> playersChanged = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        this.game = new Game(board,players, StatusGame.IN_PROGRESS);
        when(playerService.isPlayerTurn(players,6)).thenReturn(true);
        when(playerService.changePlayerTurn(game)).thenReturn(playersChanged);
        Game result = moveService.move(this.game, 6);
        game.setPlayers(playersChanged);
        assertEquals(result.getBoard().getPitList().toString(),"[1:6, 2:6, 3:6, 4:6, 5:6, 6:0, 7:1, 8:7, 9:7, 10:7, 11:7, 12:7, 13:6, 14:0]");
        assertTrue(game.getPlayers().get(1).isTurn());
    }

    @Test
    public void shouldSowOfSixthPitPlayerTop() throws GameException {
        players = Arrays.asList(new Player("Erick",false),new Player("Alex",true));
        List<Player> playersChanged = Arrays.asList(new Player("Erick",true),new Player("Alex",false));
        this.game = new Game(board,players, StatusGame.IN_PROGRESS);
        when(playerService.isPlayerTurn(players,13)).thenReturn(true);
        when(playerService.changePlayerTurn(game)).thenReturn(playersChanged);
        Game result = moveService.move(this.game, 13);
        game.setPlayers(playersChanged);
        assertEquals(result.getBoard().getPitList().toString(),"[1:7, 2:7, 3:7, 4:7, 5:7, 6:6, 7:0, 8:6, 9:6, 10:6, 11:6, 12:6, 13:0, 14:1]");
        assertTrue(game.getPlayers().get(0).isTurn());
    }




}
