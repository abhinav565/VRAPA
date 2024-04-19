package com.vrapa.xmleater.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("xmlEater")
@CrossOrigin
public class XMLProcessController {

    @GetMapping("/test")
    public List<String> getTest(){
    	List<String> a = new ArrayList<>();
    	a.add("sdcds");
        return a;
    }
}
