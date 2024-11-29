package ma.hariti.asmaa.wrm.springsecuritydocumentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello Public!";
    }

    @GetMapping("/private/hello")
    public String privateHello() {
        return "Hello Private!";
    }
}
