package com.bol.mancala.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * This class is used to receive the game's movement.
 * It is a DTO, so would be parsed into a domain class.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PitMoveDTO {

    /**
     *  It represents a pit number the should be moved.
     */
    @NotNull(message = "Value can not be null")
    private int pit;

    /**
     * It represents the game id.
     */
    @NotNull(message = "Value can not be null")
    @NotEmpty(message = "Value can not be empty")
    private String gameId;
}
