package com.bol.mancala.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class represents a pit.
 * Its position, amount of stones and if is a store
 * , that is, is a store.
 * In addition, store a position for a nextPit.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pit {
    private int position;
    private int stones;
    private boolean isStore;
    private Integer nextPit;

    /**
     * Create a pit
     *
     * @param position Position of Pit.
     * @param stones   Amount of stones.
     * @param isStore  If is a pit store (Store all balls) or not.
     */
    public Pit(int position, int stones, boolean isStore) {
        this.position = position;
        this.stones = stones;
        this.isStore = isStore;
    }

    /**
     * The method add ONE stone to the amount.
     */
    public void addStone() {
        this.stones++;
    }

    /**
     * The method add a QUANTITY of stones and sum to the current amount.
     *
     * @param stones Amount of stones to be added.
     */
    public void addStones(int stones) {
        this.stones = this.stones + stones;
    }


    /**
     * The method checks if a pit is empty.
     * That is, there is no stones.
     *
     * @return true, if is empty, false if not.
     */
    @JsonIgnore
    public boolean isEmpty() {
        return stones == 0;
    }

    /**
     * The method clear a pit.
     */
    public void clearStone() {
        this.stones = 0;
    }


    @JsonIgnore
    @Override
    public String toString() {
        return  position + ":" + stones ;
    }

}
