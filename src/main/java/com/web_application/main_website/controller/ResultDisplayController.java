package com.web_application.main_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultDisplayController {
    @GetMapping("/home-webpage")
    // Model used to store model data.
    public String helloWorld(Model model) {
        return "home-webpage";
    }
}
