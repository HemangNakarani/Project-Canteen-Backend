package io.sen.canteenia.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("hi/")
    public String Home()
    {
        return "<h1>Heyy Welcome !! This is Canteenia Server.</h1>The Software Engineering Project @DAIICT";
    }

}
