package com.example.aiyunshandemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    @GetMapping("/predict")
    public String predict() {
        // Simple AI logic or call to AI service
        return "AI prediction result";
    }
}
