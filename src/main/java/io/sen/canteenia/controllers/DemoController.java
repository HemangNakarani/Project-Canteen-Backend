package io.sen.canteenia.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String Home()
    {
        return "<h1>Ahh Shit !! Here we go.</h1>";
    }

}
