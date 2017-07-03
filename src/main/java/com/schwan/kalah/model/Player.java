package com.schwan.kalah.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class representing a player, including the pits and Kalah that the player owns
 * <p>
 * Created by Paul Schwan on 6/28/17.
 */
public class Player {

    private Logger logger = LogManager.getLogger();

    private int number;
    private String name;

    Integer[] pits = new Integer[Kalah.NUMBER_OF_PITS];
    int kalah;

    private boolean moveForward = true;
    private int unassignedSeeds = 0;

    private int lastPitPlaced = Kalah.DEFAULT_VALUE;
    private boolean endedInKalah = false;

    /**
     * Creates a new Player Object
     *
     * @param number      the players number
     * @param name        a name for the player
     * @param moveForward true if they're player 2 (moving 0->5 along the pits), false if player 1 (moving 5->0 along the pits)
     * @param seedsInPit  the starting number of seeds to put in the pits
     */
    public Player(int number, String name, boolean moveForward, int seedsInPit) {
        this.number = number;
        this.name = name;
        for (int i = 0; i < pits.length; i++) {
            pits[i] = seedsInPit;
        }
        kalah = 0;
        this.moveForward = moveForward;
    }

    /**
     * Sows as many seeds as possible in the players own pits
     *
     * @param pitToSow the pit to sow the seeds from
     */
    public void sow(int pitToSow) {
        removeSeeds(pitToSow);
        if (movingForward()) {
            pitToSow++;
        } else {
            pitToSow--;
        }
        removeUnassignedSeeds(placeSeeds(unassignedSeeds, pitToSow, true));
    }

    /**
     * The same as {@link #placeSeeds(int, int, boolean)}, except this method assumes that you want to start at
     * the players starting point (i.e. that you've just switched over from the other player)
     *
     * @param numberOfSeedsToPlace the total number of seeds to try and place
     * @param includeKalah         whether to include the players Kalah as a place to put the seeds or not
     * @return the number of seeds the method managed to place
     */
    protected int placeSeeds(int numberOfSeedsToPlace, boolean includeKalah) {
        return placeSeeds(numberOfSeedsToPlace, movingForward() ? 0 : pits.length - 1, includeKalah);
    }

    /**
     * Places as many seeds as possible in the players own pits (as possibly kalah)
     *
     * @param numberOfSeedsToPlace the total number of seeds to try and place
     * @param pit                  the pit to start in
     * @param includeKalah         whether to include the players Kalah as a place to put the seeds or not
     * @return the number of seeds the method managed to place
     */
    protected int placeSeeds(int numberOfSeedsToPlace, int pit, boolean includeKalah) {
        int numberOfSeedsPlaced = 0;
        logger.debug(getName() + "\tAiming to place " + numberOfSeedsToPlace + " seeds, starting at " + pit + ", kalah=" + includeKalah);

        while (numberOfSeedsToPlace > 0) {

            if (includeKalah && reachedKalah(pit)) {
                kalah++;
                numberOfSeedsToPlace--;
                numberOfSeedsPlaced++;
                endedInKalah = true;
                logger.debug(name + "\tplaced seed in the kalah => Seed(s) placed = " + numberOfSeedsPlaced + ", number left = " + numberOfSeedsToPlace);
            } else if (!exceededEnd(pit)) {
                pits[pit]++;
                numberOfSeedsToPlace--;
                numberOfSeedsPlaced++;
                endedInKalah = false;
                logger.debug(name + "\tplaced seed in the pit[" + pit + "] => Seed(s) placed = " + numberOfSeedsPlaced + ", number left = " + numberOfSeedsToPlace);
            } else {
                break;
            }

            // check if the last seed landed on an empty pit owned by the player
            if (includeKalah && numberOfSeedsToPlace == 0 && !reachedKalah(pit) && pits[pit] == 1) {
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

        return numberOfSeedsPlaced;
    }

    /**
     * Used when the game ends - moves all the seeds left in the players pits into their Kalah
     */
    public void tidyUp() {
        for (int i = 0; i < pits.length; i++) {
            kalah += pits[i];
            pits[i] = 0;
        }
    }

    /**
     * @return true if the player has finished, false if they haven't
     */
    public boolean finished() {
        for (Integer pit : pits)
            if (pit > 0) return false;
        return true;
    }

    public int getUnassignedSeeds() {
        return unassignedSeeds;
    }

    /**
     * @param number the number of seeds to remove from the unassigned seeds pile
     */
    public void removeUnassignedSeeds(int number) {
        unassignedSeeds -= number;
    }

    /**
     * Removes all the seeds from a pit and adds them to the unassigned seeds pile
     *
     * @param position the pit position
     */
    protected void removeSeeds(int position) {
        logger.debug("removing seeds from position " + position);
        unassignedSeeds = pits[position];
        pits[position] = 0;
    }

    /**
     * @return true if the last seed was sown in an empty pit, false if it wasn't
     */
    public boolean lastSeedPlacedInEmptyPit() {
        return lastPitPlaced != Kalah.DEFAULT_VALUE;
    }

    /**
     * @return the pit the last seed was placed in.  Will be the default value
     * if the pit wasn't empty when the seed was placed
     */
    public int pitLastSeedPlacedIn() {
        return lastPitPlaced;
    }

    /**
     * @param position the position they want to sow in
     * @return true if it's exceeded the end of the pits, false if it hasn't
     */
    private boolean exceededEnd(int position) {
        return position < 0 || position >= Kalah.NUMBER_OF_PITS;
    }

    /**
     * @param position the position they want to sow in
     * @return true if that's the kalah, false if it's not
     */
    private boolean reachedKalah(int position) {
        if (movingForward()) {
            return position == Kalah.NUMBER_OF_PITS;
        } else {
            return position == -1;
        }
    }

    /**
     * @return true if they finished the last turn in the kalah, false if they didn't
     */
    public boolean endInKalah() {
        return endedInKalah;
    }

    /**
     * Resets the players positions (if they ended in a kalah, and the pit they last sowed in)
     */
    public void reset() {
        endedInKalah = false;
        lastPitPlaced = Kalah.DEFAULT_VALUE;
    }


    public boolean movingForward() {
        return moveForward;
    }

    public Integer[] getPits() {
        return pits;
    }

    public int getKalah() {
        return kalah;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "[Player " + getNumber() + ", name=" + getName() + "]";
    }
}
