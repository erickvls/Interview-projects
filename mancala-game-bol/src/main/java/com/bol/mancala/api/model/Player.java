package com.bol.mancala.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The class represents a player.
 * It is composed by name and if is its Turn {true,false}.
 */
@Data
@AllArgsConstructor
public class Player {
    private String name;
    private boolean isTurn;

}
