package com.vrapa.xmleater.controller;

import com.vrapa.xmleater.service.XMLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


@RestController
@RequestMapping("xmlEater")
public class ConvertXmlController {

    @Autowired
    XMLProcessService xmlProcessService;

    @PostMapping("/convert")
    public String convertXmk(@RequestParam ("file") MultipartFile file , @RequestParam("targetFormat") String targetFormat){
        try{
            String message= xmlProcessService.convertXml(file,targetFormat);
            }
        catch (Exception e) {
            e.printStackTrace();
            return "Conversion Failed! Error: "+ e.getMessage();
        }
        return "ConvertXML Function call ended";
    }}
