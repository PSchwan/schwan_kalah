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

@Controller
public class KalahController {

    private Logger logger = LogManager.getLogger(KalahController.class);

    private Kalah kalah = new Kalah();

    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        System.out.println("hello world, " + this.message);
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/game")
    public String gameScreen(Map<String, Object> model) {

        model.put("Player1Pits", kalah.getPlayerOne().getPits());
        model.put("Player2Pits", kalah.getPlayerTwo().getPits());
        model.put("Player1Kalah", kalah.getPlayerOne().getKalah());
        model.put("Player2Kalah", kalah.getPlayerTwo().getKalah());
        model.put("player1sTurn", kalah.whosTurn().getNumber() == 0);
        model.put("player2sTurn", kalah.whosTurn().getNumber() == 1);

        return "gameScreen";
    }

    @RequestMapping(value = "move", method = RequestMethod.POST)
    public String move(Map<String, Object> model, @RequestParam String move) {
        try {
            logger.info("move selected:" + move);
            if (kalah.play(move)) {
                return redirectToGame(); // todo: switch this to the game over screen!
            } else {
                return redirectToGame();
            }
        } catch (InvalidMoveException e) {
            logger.error("Exception thrown! " + e.getLocalizedMessage(), e);
            return redirectToGame();
        }
    }

    private String redirectToGame() {
        return "redirect:/game";
    }

}
