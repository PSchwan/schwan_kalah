package com.schwan.kalah.model;
import com.schwan.kalah.exception.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Paul Schwan on 6/28/17.
 */
public class KalahTest {

    Kalah kalah;

    @Before
    public void setUp() throws Exception {
        kalah = new Kalah(Kalah.GAME_TYPE_SIX_STONES);
    }

    @Test
    public void boardIsSetupCorrectlyAtStartOfGame() throws Exception {

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(0, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(6, playerOnePits[0].intValue());
        assertEquals(6, playerOnePits[1].intValue());
        assertEquals(6, playerOnePits[2].intValue());
        assertEquals(6, playerOnePits[3].intValue());
        assertEquals(6, playerOnePits[4].intValue());
        assertEquals(6, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(0, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(6, playerTwoPits[0].intValue());
        assertEquals(6, playerTwoPits[1].intValue());
        assertEquals(6, playerTwoPits[2].intValue());
        assertEquals(6, playerTwoPits[3].intValue());
        assertEquals(6, playerTwoPits[4].intValue());
        assertEquals(6, playerTwoPits[5].intValue());
    }

    @Test
    public void sow_throwsExceptionWithAMinusPitNumber() throws Exception {
        try {
            kalah.sow(0, -1);
            fail("Expected an exception to be thrown on the last line");
        } catch (InvalidMoveException e) {
            assertEquals("Cannot sow with a pit of [-1]", e.getMessage());
        }
    }

    @Test
    public void sow_throwsExceptionWithAHighPitNumber() throws Exception {
        try {
            kalah.sow(0, 10);
            fail("Expected an exception to be thrown on the last line");
        } catch (InvalidMoveException e) {
            assertEquals("Cannot sow with a pit of [10]", e.getMessage());
        }
    }

    @Test
    public void sow_worksWithTheFirstPit() throws Exception {
        kalah.sow(0, 1);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(1, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(7, playerOnePits[0].intValue());
        assertEquals(0, playerOnePits[1].intValue());
        assertEquals(6, playerOnePits[2].intValue());
        assertEquals(6, playerOnePits[3].intValue());
        assertEquals(6, playerOnePits[4].intValue());
        assertEquals(6, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(0, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(7, playerTwoPits[0].intValue());
        assertEquals(7, playerTwoPits[1].intValue());
        assertEquals(7, playerTwoPits[2].intValue());
        assertEquals(7, playerTwoPits[3].intValue());
        assertEquals(6, playerTwoPits[4].intValue());
        assertEquals(6, playerTwoPits[5].intValue());
    }

    @Test
    public void sow_worksWithTheLastPit() throws Exception {
        kalah.sow(0, 5);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(1, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(7, playerOnePits[0].intValue());
        assertEquals(7, playerOnePits[1].intValue());
        assertEquals(7, playerOnePits[2].intValue());
        assertEquals(7, playerOnePits[3].intValue());
        assertEquals(7, playerOnePits[4].intValue());
        assertEquals(0, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(0, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(6, playerTwoPits[0].intValue());
        assertEquals(6, playerTwoPits[1].intValue());
        assertEquals(6, playerTwoPits[2].intValue());
        assertEquals(6, playerTwoPits[3].intValue());
        assertEquals(6, playerTwoPits[4].intValue());
        assertEquals(6, playerTwoPits[5].intValue());
    }

    @Test
    public void sow_worksForPlayerTwo() throws Exception {
        kalah.sow(1, 5);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(0, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(6, playerOnePits[0].intValue());
        assertEquals(7, playerOnePits[1].intValue());
        assertEquals(7, playerOnePits[2].intValue());
        assertEquals(7, playerOnePits[3].intValue());
        assertEquals(7, playerOnePits[4].intValue());
        assertEquals(7, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(1, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(6, playerTwoPits[0].intValue());
        assertEquals(6, playerTwoPits[1].intValue());
        assertEquals(6, playerTwoPits[2].intValue());
        assertEquals(6, playerTwoPits[3].intValue());
        assertEquals(6, playerTwoPits[4].intValue());
        assertEquals(0, playerTwoPits[5].intValue());

    }

    @Test
    public void sow_worksWhenPlacingLoadsOfStones() throws Exception {

        kalah.player1.pits[1] = 20;
        kalah.sow(0, 1);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(2, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(8, playerOnePits[0].intValue());
        assertEquals(1, playerOnePits[1].intValue());
        assertEquals(7, playerOnePits[2].intValue());
        assertEquals(7, playerOnePits[3].intValue());
        assertEquals(7, playerOnePits[4].intValue());
        assertEquals(7, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(0, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(8, playerTwoPits[0].intValue());
        assertEquals(8, playerTwoPits[1].intValue());
        assertEquals(8, playerTwoPits[2].intValue());
        assertEquals(8, playerTwoPits[3].intValue());
        assertEquals(8, playerTwoPits[4].intValue());
        assertEquals(7, playerTwoPits[5].intValue());
    }

    @Test
    public void sow_worksWhenPlayerFinishesInAnEmptyPit() throws Exception {

        kalah.player2.pits[0] = 1;
        kalah.player2.pits[1] = 0;
        kalah.player2.pits[2] = 3;
        kalah.player2.pits[3] = 2;
        kalah.player2.pits[4] = 3;
        kalah.player2.pits[5] = 0;
        kalah.sow(1, 2);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(0, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(6, playerOnePits[0].intValue());
        assertEquals(6, playerOnePits[1].intValue());
        assertEquals(6, playerOnePits[2].intValue());
        assertEquals(6, playerOnePits[3].intValue());
        assertEquals(6, playerOnePits[4].intValue());
        assertEquals(0, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(7, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(1, playerTwoPits[0].intValue());
        assertEquals(0, playerTwoPits[1].intValue());
        assertEquals(0, playerTwoPits[2].intValue());
        assertEquals(3, playerTwoPits[3].intValue());
        assertEquals(4, playerTwoPits[4].intValue());
        assertEquals(0, playerTwoPits[5].intValue());

    }

    @Test
    public void sow_worksCorrectlyWhenPlayerFinishesInAOpponentsEmptyPit() throws Exception  {

        kalah.player1.pits[0] = 1;
        kalah.player1.pits[1] = 4;
        kalah.player1.pits[2] = 3;
        kalah.player1.pits[3] = 2;
        kalah.player1.pits[4] = 3;
        kalah.player1.pits[5] = 0;

        kalah.player2.pits[0] = 0;
        kalah.player2.pits[1] = 0;
        kalah.player2.pits[2] = 0;
        kalah.player2.pits[3] = 2;
        kalah.player2.pits[4] = 3;
        kalah.player2.pits[5] = 0;
        kalah.sow(0, 1);

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(1, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(2, playerOnePits[0].intValue());
        assertEquals(0, playerOnePits[1].intValue());
        assertEquals(3, playerOnePits[2].intValue());
        assertEquals(2, playerOnePits[3].intValue());
        assertEquals(3, playerOnePits[4].intValue());
        assertEquals(0, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(0, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(1, playerTwoPits[0].intValue());
        assertEquals(1, playerTwoPits[1].intValue());
        assertEquals(0, playerTwoPits[2].intValue());
        assertEquals(2, playerTwoPits[3].intValue());
        assertEquals(3, playerTwoPits[4].intValue());
        assertEquals(0, playerTwoPits[5].intValue());

    }

    @Test
    public void getWinner_worksWithPlayer1Winning() {
        kalah.player1.kalah = 10;
        kalah.player2.kalah = 8;

        assertEquals(kalah.getPlayerOne(), kalah.getWinner());
    }

    @Test
    public void getWinner_worksWithPlayer2Winning() {
        kalah.player1.kalah = 3;
        kalah.player2.kalah = 8;

        assertEquals(kalah.getPlayerTwo(), kalah.getWinner());
    }

    @Test
    public void getWinner_worksWithADraw() {
        kalah.player1.kalah = 10;
        kalah.player2.kalah = 10;

        assertEquals(null, kalah.getWinner());
    }

    @Test
    public void switchPlayer_from1to2() {
        kalah.whosTurn = kalah.player1;
        kalah.switchPlayer();
        assertEquals(kalah.player2, kalah.whosTurn);
    }

    @Test
    public void switchPlayer_from2to1() {
        kalah.whosTurn = kalah.player2;
        kalah.switchPlayer();
        assertEquals(kalah.player1, kalah.whosTurn);
    }

    @Test
    public void play_swapsActivePlayerAfterATurn_1to2() throws Exception  {
        kalah.whosTurn = kalah.player1;
        assertFalse(kalah.play(3));
        assertEquals(kalah.player2, kalah.whosTurn);
    }

    @Test
    public void play_swapsActivePlayerAfterATurn_2to1() throws Exception  {
        kalah.whosTurn = kalah.player2;
        assertFalse(kalah.play(3));
        assertEquals(kalah.player1, kalah.whosTurn);
    }

    @Test
    public void play_worksForAnEntireGameNormally() throws Exception {

        kalah.whosTurn = kalah.player1;

        assertFalse(kalah.play(3));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(2));
        assertFalse(kalah.play(2));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(2));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(2));
        assertFalse(kalah.play(2));
        assertFalse(kalah.play(5));
        assertFalse(kalah.play(1));
        assertFalse(kalah.play(4));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(3));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(0));
        assertFalse(kalah.play(0));

        assertTrue(kalah.play(2)); // finally the game ends (hence true instead of false

        System.out.println(kalah.printBoard());

        Integer[] playerOnePits = kalah.getPlayerOne().getPits();
        assertEquals(32, kalah.getPlayerOne().getKalah());
        assertEquals(6, playerOnePits.length);
        assertEquals(0, playerOnePits[0].intValue());
        assertEquals(0, playerOnePits[1].intValue());
        assertEquals(0, playerOnePits[2].intValue());
        assertEquals(0, playerOnePits[3].intValue());
        assertEquals(0, playerOnePits[4].intValue());
        assertEquals(0, playerOnePits[5].intValue());

        Integer[] playerTwoPits = kalah.getPlayerTwo().getPits();
        assertEquals(40, kalah.getPlayerTwo().getKalah());
        assertEquals(6, playerTwoPits.length);
        assertEquals(0, playerTwoPits[0].intValue());
        assertEquals(0, playerTwoPits[1].intValue());
        assertEquals(0, playerTwoPits[2].intValue());
        assertEquals(0, playerTwoPits[3].intValue());
        assertEquals(0, playerTwoPits[4].intValue());
        assertEquals(0, playerTwoPits[5].intValue());
        assertEquals(kalah.getPlayerTwo(), kalah.getWinner()); // player two wins!
    }

}