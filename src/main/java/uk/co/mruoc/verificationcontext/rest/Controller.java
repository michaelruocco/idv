package uk.co.mruoc.verificationcontext.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Controller {

    @GetMapping
    public String hello() {
        return "Hello!";
    }

}
