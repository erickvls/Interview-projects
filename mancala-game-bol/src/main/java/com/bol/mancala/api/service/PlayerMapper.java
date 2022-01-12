package com.bol.mancala.api.service;

import com.bol.mancala.api.dto.PlayersDTO;
import com.bol.mancala.api.model.Player;

import java.util.List;

@FunctionalInterface
public interface PlayerMapper {
    /**
     * The method maps DTO coming from request into a domain object.
     *
     * @param dto Information about the players
     * @return It maps into a list with players, size 2.
     */
    List<Player> toModel(PlayersDTO dto);
}
