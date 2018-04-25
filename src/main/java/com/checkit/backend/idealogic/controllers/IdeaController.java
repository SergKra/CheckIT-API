package com.checkit.backend.idealogic.controllers;

import com.checkit.backend.idealogic.domains.Idea;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdeaController {

    @RequestMapping("/idea")
    public Idea idea(@RequestParam(value="name", required = false, defaultValue = "world") String name){
        return new Idea(name="Lord of Rings", "Tolkien", "There is wonderful story about hobbits adventures");
    }
}
