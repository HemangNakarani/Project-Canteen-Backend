package io.sen.canteenia.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String Home()
    {
        return "<h1>This is Canteenia Server deployed on AWS.</h1>The Software Engineering Project @DAIICT";
    }

}
