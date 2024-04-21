package com.vrapa.xmleater.controller;

import com.vrapa.xmleater.service.XMLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("xmlEater")
@CrossOrigin
public class ConvertAemXmlController {

    @Autowired
    XMLProcessService xmlProcessService;

    @PostMapping("/convertAemXml")
    public String convertXmk(@RequestParam ("file") MultipartFile file ){
        try{
            String message= xmlProcessService.convertAemXml(file);
            }
        catch (Exception e) {
            e.printStackTrace();
            return "Conversion Failed! Error: "+ e.getMessage();
        }
        return "ConvertXML Function call ended";
    }}
