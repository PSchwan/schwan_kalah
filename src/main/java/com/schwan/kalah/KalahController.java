package com.schwan.kalah;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KalahController {

    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/welcome")
    public String welcome(Map<String, Object> model) {
        System.out.println("hello world, " + this.message);
        model.put("message", this.message);
        return "welcome";


        /*ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("message", this.message);
        return modelAndView;*/
    }

}
