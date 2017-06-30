package com.schwan.kalah.model;

import com.schwan.kalah.exception.InvalidMoveException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by Paul Schwan on 6/28/17.
 */
public class Kalah {

    public static final int GAME_TYPE_SIX_STONES = 6;
    public static final int NUMBER_OF_PITS = 6;
    public static final int DEFAULT_VALUE = -1000; // make it a number that'll never appear otherwise (-1 is the Kalah for one player...)


    protected Logger logger = LogManager.getLogger();

    protected Player player1;
    protected Player player2;

    protected Player whosTurn;

    protected int startingStonesInAPit;

    public Kalah() {
        this(GAME_TYPE_SIX_STONES);
    }

    public Kalah(int numberOfStones) {
        logger.debug("Starting a new game of Kalah with " + numberOfStones + " stones");
        switch (numberOfStones) {
            case GAME_TYPE_SIX_STONES:
                break;
            default:
                throw new UnsupportedOperationException("This game only supports 6 stones currently");
        }

        startingStonesInAPit = numberOfStones;

        player1 = new Player(0, "Player1", false, startingStonesInAPit);
        player2 = new Player(1, "Player2", true, startingStonesInAPit);

        Random random = new Random();
        if (random.nextBoolean()) {
            whosTurn = player1;
        } else {
            whosTurn = player2;
        }
    }

    public boolean play(String original) throws InvalidMoveException {
        try {
            int pitToSow = Integer.parseInt(original);
            return play(pitToSow);
        } catch (NumberFormatException e) {
            //TODO: how to handle this?
            return false; // currently just ignores it and returns - not brilliant, but lets the player try again
        }
    }

    /**
     * @param pitToSow the pit the next player has chosen
     * @return true if the game ended (someone finished) or false if it continues
     * @throws InvalidMoveException
     */
    public boolean play(int pitToSow) throws InvalidMoveException {

        sow(whosTurn, pitToSow);

        System.out.println(printBoard());

        if (gameOver()) {
            player1.tidyUp();
            player2.tidyUp();
            return true;
        } else {
            switchPlayer();
            return false;
        }

    }

    public void sow(Player player, int pitToSow) throws InvalidMoveException {

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
        while (playerWhosTurnItIs.getUnassignedStones() > 0) {
            playerWhosTurnItIs.removeUnassignedStones(otherPlayer.placeStones(playerWhosTurnItIs.getUnassignedStones(), otherPlayer.movingForward() ? 0 : NUMBER_OF_PITS - 1, playerWhosTurnItIs.equals(otherPlayer)));

            if (activePlayer.equals(player1)) {
                activePlayer = player2;
                otherPlayer = player1;
            } else {
                activePlayer = player1;
                otherPlayer = player2;
            }
        }

        if (playerWhosTurnItIs.equals(activePlayer)) {
            if (playerWhosTurnItIs.lastSeedPlacedInEmptyPit()) {

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


    }

    public boolean gameOver() {
        return player1.finished() || player2.finished();
    }

    public Player getWinner() {
        if (player1.getKalah() > player2.getKalah()) {
            return player1;
        } else if (player2.getKalah() > player1.getKalah()) {
            return player2;
        } else {
            return null;
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


    void switchPlayer() {
        if (whosTurn.equals(player1)) {
            whosTurn = player2;
        } else {
            whosTurn = player1;
        }

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
                "\n";
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
