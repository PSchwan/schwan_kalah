package com.schwan.kalah.model;

import com.schwan.kalah.exception.InvalidMoveException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Object representing a single game of Kalah, including everything that's needed for the game to run (players, the board, etc.)
 * <p>
 * Created by Paul Schwan on 6/28/17.
 */
public class Kalah {

    public static final int GAME_TYPE_SIX_SEEDS = 6;
    public static final int NUMBER_OF_PITS = 6;
    public static final int DEFAULT_VALUE = -1000; // make it a number that'll never appear otherwise (-1 is the Kalah for one player...)

    private Logger logger = LogManager.getLogger();

    Player player1;
    Player player2;

    /** Reflects who's turn it currently is (either player1 or player2) */
    Player whosTurn;

    public Kalah() {
        this(GAME_TYPE_SIX_SEEDS);
    }

    public Kalah(int numberOfSeeds) {
        logger.debug("Starting a new game of Kalah with " + numberOfSeeds + " seeds");
        switch (numberOfSeeds) {
            case GAME_TYPE_SIX_SEEDS:
                break;
            // TODO: PS 03/07/2017: add other game modes (3, 4 etc.) here as they become supported
            default:
                throw new UnsupportedOperationException("This game only supports 6 seeds currently");
        }

        player1 = new Player(0, "Player1", false, numberOfSeeds);
        player2 = new Player(1, "Player2", true, numberOfSeeds);

        // decide who starts
        Random random = new Random();
        if (random.nextBoolean()) {
            whosTurn = player1;
        } else {
            whosTurn = player2;
        }
    }

    /**
     * Plays a single turn of the game
     *
     * @param original the pit the next player has chosen, in String format (i.e. "2")
     * @return true if the game ended (someone finished) or false if it continues
     * @throws InvalidMoveException if the pit selected is invalid
     */
    public boolean play(String original) throws InvalidMoveException {
        try {
            int pitToSow = Integer.parseInt(original);
            return play(pitToSow);
        } catch (NumberFormatException e) {
            logger.error("input string was invalid [input=" + original + "] " + e.getMessage(), e);
            return false; // currently just ignores it and returns - not brilliant, but lets the player try again
        }
    }

    /**
     * Plays a single turn of the game
     *
     * @param pitToSow the pit the next player has chosen
     * @return true if the game ended (someone finished) or false if it continues
     * @throws InvalidMoveException if the pit selected is invalid
     */
    public boolean play(int pitToSow) throws InvalidMoveException {

        sow(whosTurn, pitToSow);

        if (gameOver()) {
            player1.tidyUp();
            player2.tidyUp();
            logger.debug(printBoard());
            return true;
        } else {
            if (!whosTurn.endInKalah()) {
                switchPlayer();
            }
            logger.debug(printBoard());
            return false;
        }

    }

    /**
     * Sows the seeds for a single player
     *
     * @param player   which player is sowing the seeds
     * @param pitToSow which pit they wish to take the seeds from
     * @throws InvalidMoveException if the pit selected is invalid
     */
    public void sow(Player player, int pitToSow) throws InvalidMoveException {

        // use local variables to make unit testing easier
        Player playerWhosTurnItIs;
        Player activePlayer;
        Player otherPlayer;

        if (pitToSow < 0 || pitToSow >= NUMBER_OF_PITS) {
            throw new InvalidMoveException("Cannot sow with a pit of [" + pitToSow + "]");
        }

        if (player == player1) {
            playerWhosTurnItIs = player1;
            activePlayer = player1;
            otherPlayer = player2;
        } else if (player == player2) {
            playerWhosTurnItIs = player2;
            activePlayer = player2;
            otherPlayer = player1;
        } else {
            throw new InvalidMoveException("Invalid player selected");
        }

        logger.info(playerWhosTurnItIs.getName() + " chose to sow at position " + pitToSow);

        playerWhosTurnItIs.sow(pitToSow);

        // while loop to keep switching between the two players until all the seeds a sown
        while (playerWhosTurnItIs.getUnassignedSeeds() > 0) {

            activePlayer.reset();
            otherPlayer.reset();

            playerWhosTurnItIs.removeUnassignedSeeds(otherPlayer.placeSeeds(playerWhosTurnItIs.getUnassignedSeeds(), playerWhosTurnItIs.equals(otherPlayer)));

            // switch the active player, in case more seeds still need to be sown
            if (activePlayer.equals(player1)) {
                activePlayer = player2;
                otherPlayer = player1;
            } else {
                activePlayer = player1;
                otherPlayer = player2;
            }
        }

        // if the player has finished in an empty pit they own, they take both theirs and the oppositions seeds and kalah them
        if (playerWhosTurnItIs.equals(activePlayer) && playerWhosTurnItIs.lastSeedPlacedInEmptyPit()) {

            logger.info("Seed left in empty pit[" + playerWhosTurnItIs.pitLastSeedPlacedIn() +
                    "]: moving " + activePlayer.getName() + "'s " + activePlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()] +
                    " seeds and " + otherPlayer.getName() + "'s " + otherPlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()] +
                    " seeds to " + activePlayer.getName() + "'s Kalah");

            activePlayer.kalah += activePlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()];
            activePlayer.kalah += otherPlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()];
            activePlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()] = 0;
            otherPlayer.pits[playerWhosTurnItIs.pitLastSeedPlacedIn()] = 0;
        }
    }

    /**
     * @return true if the game is over (i.e. one player has finished), false if both players still have seeds
     */
    public boolean gameOver() {
        return player1.finished() || player2.finished();
    }

    /**
     * @return the currently winning player, or null if they're drawing.  Note: this doesn't mean the player has won,
     * just that they are currently winning!
     */
    public Player getWinner() {
        if (player1.getKalah() > player2.getKalah()) {
            return player1;
        } else if (player2.getKalah() > player1.getKalah()) {
            return player2;
        } else {
            return null;
        }
    }

    /**
     * Switch who's turn it is
     */
    void switchPlayer() {
        if (whosTurn.equals(player1)) {
            whosTurn = player2;
        } else {
            whosTurn = player1;
        }

    }


    public Player whosTurn() {
        return whosTurn;
    }

    public Player getPlayerOne() {
        return player1;
    }

    public Player getPlayerTwo() {
        return player2;
    }


    String printBoard() {
        return "\n" +
                "Board State:\n" +
                printPits(player1.getPits()) +
                "\n" +
                "\t" + player1.getKalah() +
                "\t\t\t\t\t\t\t" + player2.getKalah() +
                "\n" +
                printPits(player2.getPits()) +
                "\nIt is " + whosTurn.getName() + "'s turn";
    }

    private String printPits(Integer[] pits) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i = 0; i < pits.length; i++) {
            sb.append("\t").append(i).append(":").append(pits[i]);
        }
        return sb.toString();
    }

}
