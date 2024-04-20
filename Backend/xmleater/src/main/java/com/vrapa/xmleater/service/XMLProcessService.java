package com.vrapa.xmleater.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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

@Service
public class XMLProcessService {

    public String convertXml(MultipartFile file , String targetFormat){

        try{
        //Load the source XML File
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder= factory.newDocumentBuilder();
        Document doc= builder.parse(file.getInputStream());

        //Create a new document for the target format
        Document targetDoc=builder.newDocument();
        Element targetRootElement= targetDoc.createElement(targetFormat);
        targetDoc.appendChild(targetRootElement);



        //TODO Mapping to be done
        NodeList sourceElements=doc.getDocumentElement().getChildNodes();
        for (int i=0; i< sourceElements.getLength();i++){
            Element sourceElem = (Element) sourceElements.item(i);
            Element targetElem = targetDoc.createElement(sourceElem.getTagName());

            //Copy attributes
            for (int j=0; j<sourceElem.getAttributes().getLength(); j++){
                targetElem.setAttribute(sourceElem.getAttributes().item(j).getNodeName(), sourceElem.getAttributes().item(j).getNodeValue());


                //Copy Text Content
                targetElem.setTextContent(sourceElem.getTextContent());
                targetRootElement.appendChild(targetElem);

                //Write Target XML to a file
                TransformerFactory transformerFactory= TransformerFactory.newInstance();
                Transformer transformer= transformerFactory.newTransformer();
                DOMSource source= new DOMSource(targetDoc);
                StreamResult result= new StreamResult( new File("target.xml"));
                transformer.transform(source,result);
                return "Conversion successful! Target file saved as target.xml";

            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "Conversion Failed! Error: "+ e.getMessage();
    }
        return "ConvertXML Function call ended";
}
}
