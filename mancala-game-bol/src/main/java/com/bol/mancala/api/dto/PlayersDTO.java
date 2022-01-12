package com.bol.mancala.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This class is used to receive the player's name when a game needs to be started.
 * It is a DTO, so would be parsed into a domain class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayersDTO {

    /**
     *  It is a player one name.
     */
    @NotNull(message = "Value can not be null")
    @NotEmpty(message = "Value can not be empty")
    private String playerOneName;

    /**
     * It is a player two name.
     */
    @NotNull(message = "Value can not be null")
    @NotEmpty(message = "Value can not be empty")
    private String playerTwoName;

}
