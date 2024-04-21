package com.vrapa.xmleater.controller;

import com.vrapa.xmleater.service.XMLProcessService;
import com.vrapa.xmleater.service.XMLProcessServiceLifeRay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("xmlEater")
@CrossOrigin
public class ConvertAemXmlController {

    @Autowired
    XMLProcessService xmlProcessService;

    @Autowired
    XMLProcessServiceLifeRay xmlProcessServiceLifeRay;
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
    }
    @PostMapping("/convert")
    public String convertXmk(@RequestParam ("file") MultipartFile file ){
        try{
            String message= xmlProcessServiceLifeRay.convertXml(file);
            }
        catch (Exception e) {
            e.printStackTrace();
            return "Conversion Failed! Error: "+ e.getMessage();
        }
        return "ConvertXML Function call ended";
    }    
}
