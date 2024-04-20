package com.vrapa.xmleater.controller;

import com.vrapa.xmleater.service.XMLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("xmlEater")
public class ConvertAemXmlController {

    @Autowired
    XMLProcessService xmlProcessService;

    @PostMapping("/convertAemXml")
    public String convertXmk(@RequestParam ("file") MultipartFile file , @RequestParam("targetFormat") String targetFormat){
        try{
            String message= xmlProcessService.convertAemXml(file,targetFormat);
            }
        catch (Exception e) {
            e.printStackTrace();
            return "Conversion Failed! Error: "+ e.getMessage();
        }
        return "ConvertXML Function call ended";
    }}
