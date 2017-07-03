package com.schwan.kalah.controller;

import java.util.Map;

import com.schwan.kalah.exception.InvalidMoveException;
import com.schwan.kalah.model.Kalah;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller covering all the endpoints related to the Kalah game
 */
@Controller
public class KalahController {

    private Logger logger = LogManager.getLogger(KalahController.class);

    private Kalah kalah = new Kalah();

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        kalah = new Kalah();
        return "welcome";
    }

    @RequestMapping("/game")
    public String gameScreen(Map<String, Object> model) {

        model.put("Player1Pits", kalah.getPlayerOne().getPits());
        model.put("Player2Pits", kalah.getPlayerTwo().getPits());
        model.put("Player1Kalah", kalah.getPlayerOne().getKalah());
        model.put("Player2Kalah", kalah.getPlayerTwo().getKalah());

        if (kalah.gameOver()) {
            model.put("player1sTurn", false);
            model.put("player2sTurn", false);
            model.put("gameOver", true);
            if (kalah.getWinner() == null) { // means the game ended a draw
                model.put("draw", true);
            } else {
                model.put("draw", false);
                model.put("winner", kalah.getWinner());
            }

        } else {
            model.put("player1sTurn", kalah.whosTurn().getNumber() == 0);
            model.put("player2sTurn", kalah.whosTurn().getNumber() == 1);
        }

        return "gameScreen";
    }

    @RequestMapping(value = "move", method = RequestMethod.POST)
    public String move(@RequestParam String move) {
        try {
            kalah.play(move);
            return redirectToGame();
        } catch (InvalidMoveException e) {
            logger.error("Exception thrown! " + e.getLocalizedMessage(), e);
            return redirectToGame();
        }
    }

    private String redirectToGame() {
        return "redirect:/game";
    }

}
