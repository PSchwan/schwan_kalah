package com.schwan.kalah;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KalahController {

    // inject via application.properties
    //@Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/test")
    public String welcome(Map<String, Object> model) {
        System.out.println("hello world");
        model.put("message", this.message);
        return "welcome";
    }

}
