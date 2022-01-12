package com.bol.mancala.api.model;

import com.bol.mancala.api.exceptions.PitNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * The class represents the board of game.
 * The board is composed by a list of Pit.
 * The Pit is composed as a LinkedList (circular) pointing for the next pit.
 */
@Data
@NoArgsConstructor
public class Board {

    @JsonIgnore
    private Pit head;
    @JsonIgnore
    private Pit tail;

    private List<Pit> pitList;

    /**
     * Creates a board setting positions from 1 to 14.
     * From 1 to 6 belongs to Player one.
     * From 8 to 13 belongs to Player two.
     * The numbers 7 and 14 are a PitStore - The stones inside it, can not be moved
     *
     * @param stonesPerPit The amount of stones
     */
    public Board(int stonesPerPit) {
        pitList = new LinkedList<>();
        this.insert(1, stonesPerPit, false);
        this.insert(2, stonesPerPit, false);
        this.insert(3, stonesPerPit, false);
        this.insert(4, stonesPerPit, false);
        this.insert(5, stonesPerPit, false);
        this.insert(6, stonesPerPit, false);
        this.insert(7, 0, true);
        this.insert(8, stonesPerPit, false);
        this.insert(9, stonesPerPit, false);
        this.insert(10, stonesPerPit, false);
        this.insert(11, stonesPerPit, false);
        this.insert(12, stonesPerPit, false);
        this.insert(13, stonesPerPit, false);
        this.insert(14, 0, true);

    }

    /**
     * Inserts a position for a pit.
     * It is a circular list.
     * When one pit is added, the pit before sets next pit as a new one.
     *
     * @param position The position of pit.
     * @param stones   The amount of stones per pit.
     * @param isStore  It is a boolean value that defines if a pit is store or not.
     */
    private void insert(int position, int stones, boolean isStore) {
        Pit newPit = new Pit(position, stones, isStore);
        if (head == null) {
            head = newPit;
        } else {
            tail.setNextPit(newPit.getPosition());
        }
        tail = newPit;
        tail.setNextPit(head.getPosition());
        pitList.add(tail);
    }


    /**
     * Returns a Pit when receive a position number.
     *
     * @param pitPosition The position of pit that needs to be retrieved.
     * @return A Pit if found
     * @throws PitNotFoundException The position doesn't exists.
     */
    public Pit getPit(int pitPosition) throws PitNotFoundException {
        try {
            return this.pitList.get(pitPosition - 1);
        } catch (NullPointerException ex) {
            throw new PitNotFoundException(ex.getMessage());
        }
    }

}
