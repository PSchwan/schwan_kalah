package com.schwan.kalah.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Paul Schwan on 6/28/17.
 */
public class PlayerTest {
    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player(0, "testPlayer", true, 6);
    }

    @Test
    public void outOfSeeds_correctAtStartOfGame() throws Exception {
        assertFalse(player.finished());
    }

    @Test
    public void outOfSeeds_correctWhenOutOfSeeds() throws Exception {
        player.pits[0] = 0;
        player.pits[1] = 0;
        player.pits[2] = 0;
        player.pits[3] = 0;
        player.pits[4] = 0;
        player.pits[5] = 0;

        assertTrue(player.finished());
    }

    @Test
    public void outOfSeeds_correctWhenSingleSeedLeft() throws Exception {
        player.pits[0] = 0;
        player.pits[1] = 0;
        player.pits[2] = 0;
        player.pits[3] = 1;
        player.pits[4] = 0;
        player.pits[5] = 0;

        assertFalse(player.finished());
    }

    @Test
    public void tidyUp_withSeedsLeftInThePits() {
        player.kalah = 3;
        player.pits[0] = 1;
        player.pits[1] = 2;
        player.pits[2] = 0;
        player.pits[3] = 6;
        player.pits[4] = 1;
        player.pits[5] = 3;

        player.tidyUp();

        assertEquals(0, player.pits[0].intValue());
        assertEquals(0, player.pits[1].intValue());
        assertEquals(0, player.pits[2].intValue());
        assertEquals(0, player.pits[3].intValue());
        assertEquals(0, player.pits[4].intValue());
        assertEquals(0, player.pits[5].intValue());
        assertEquals(16, player.kalah);

    }

    @Test
    public void tidyUp_withoutAnySeedsLeft() {
        player.kalah = 4;
        player.pits[0] = 0;
        player.pits[1] = 0;
        player.pits[2] = 0;
        player.pits[3] = 0;
        player.pits[4] = 0;
        player.pits[5] = 0;

        player.tidyUp();

        assertEquals(0, player.pits[0].intValue());
        assertEquals(0, player.pits[1].intValue());
        assertEquals(0, player.pits[2].intValue());
        assertEquals(0, player.pits[3].intValue());
        assertEquals(0, player.pits[4].intValue());
        assertEquals(0, player.pits[5].intValue());
        assertEquals(4, player.kalah);

    }
}