package com.bol.mancala.api.service.impl;

import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.model.Player;
import com.bol.mancala.api.service.PlayerMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Class responsible to parse DTO into Domain class.
 */
@Service
public class PlayerMapperServiceImpl implements PlayerMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> toModel(PlayersDTO playersDTO) {
        return Arrays.asList(new Player(playersDTO.getPlayerOneName(), false), new Player(playersDTO.getPlayerTwoName(), false));
    }
}
