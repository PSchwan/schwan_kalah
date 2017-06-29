package com.schwan.kalah.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Paul Schwan on 6/28/17.
 */
public class Player {

    protected Logger logger = LogManager.getLogger();

    protected Integer[] pits = new Integer[Kalah.NUMBER_OF_PITS];
    int kalah;

    private boolean moveForward = true;
    private int unassignedStones = 0;
    private int lastPitPlaced = Kalah.DEFAULT_VALUE;
    private String name;

    public Player(String name, boolean moveForward) {
        this.name = name;
        for (int i = 0; i < pits.length; i++) {
            pits[i] = Kalah.STONES_TO_START_IN_A_BIT;
        }
        kalah = 0;
        this.moveForward = moveForward;
    }

    public boolean movingForward() {
        return moveForward;
    }

    public boolean finished() {
        for (Integer pit : pits) {
            if (pit > 0) {
                return false;
            }
        }
        return true;
    }

    public int getUnassignedStones() {
        return unassignedStones;
    }

    public void removeUnassignedStones(int number) {
        unassignedStones -= number;
    }

    public Integer[] getPits() {
        return pits;
    }

    public int getKalah() {
        return kalah;
    }

    protected void removeStones(int position) {
        logger.debug("removing stones from position " + position);
        unassignedStones = pits[position];
        pits[position] = 0;
    }

    public boolean lastSeedPlacedInEmptyPit() {
        return lastPitPlaced != Kalah.DEFAULT_VALUE;
    }

    public int pitLastSeedPlacedIn() {
        return lastPitPlaced;
    }

    public void sow(int pitToSow) {
        removeStones(pitToSow);
        if (movingForward()) {
            pitToSow++;
        } else {
            pitToSow--;
        }
        removeUnassignedStones(placeStones(unassignedStones, pitToSow, true));
    }

    protected int placeStones(int numberOfStonesToPlace, int pit, boolean includeKalah) {
        int numberOfStonesPlaced = 0;
        logger.debug(name + "\tAiming to place " + numberOfStonesToPlace + " stones, starting at " + pit + ", kalah=" + includeKalah);

        while (numberOfStonesToPlace > 0) {

            if (includeKalah && reachedKalah(pit)) {
                kalah++;
                numberOfStonesToPlace--;
                numberOfStonesPlaced++;
                logger.debug(name + "\tplaced stone in the kalah => Stones placed = " + numberOfStonesPlaced + ", number left = " + numberOfStonesToPlace);
            } else if (!exceededEnd(pit)) {
                pits[pit]++;
                numberOfStonesToPlace--;
                numberOfStonesPlaced++;
                logger.debug(name + "\tplaced stone in the pit[" + pit + "] => Stones placed = " + numberOfStonesPlaced + ", number left = " + numberOfStonesToPlace);
            } else {
                break;
            }

            // check if the last seed landed on an empty pit owned by the player
            if(includeKalah && numberOfStonesToPlace == 0 && !reachedKalah(pit) && pits[pit] == 1) {
                lastPitPlaced = pit;
            } else {
                lastPitPlaced = Kalah.DEFAULT_VALUE;
            }

            if (movingForward()) {
                pit++;
            } else {
                pit--;
            }
        }

        return numberOfStonesPlaced;
    }

    /**
     * Used when the game ends - moves all the stones left in the players pits into their Kalah
     */
    public void tidyUp() {

        for(int i = 0; i < pits.length; i++) {
            kalah += pits[i];
            pits[i] = 0;
        }

    }

    private boolean exceededEnd(int position) {
        return position < 0 || position >= Kalah.NUMBER_OF_PITS;
    }

    private boolean reachedKalah(int position) {
        if (movingForward()) {
            return position == Kalah.NUMBER_OF_PITS;
        } else {
            return position == -1;
        }
    }

    public String getName() {
        return name;
    }

}
