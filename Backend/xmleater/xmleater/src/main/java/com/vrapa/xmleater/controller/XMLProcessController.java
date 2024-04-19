package com.vrapa.xmleater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("xmlEater")
@CrossOrigin
public class XMLProcessController {

    @GetMapping("/test")
    public String getTest(){
        return "Test Request";
    }
}
