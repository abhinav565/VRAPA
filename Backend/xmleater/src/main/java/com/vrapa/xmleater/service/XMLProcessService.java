package com.vrapa.xmleater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class XMLProcessService {

    @Autowired
    FileSavingService fileSavingService;
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

    public String convertAemXml(MultipartFile file) {
        try{
            //Load the source XML File
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document sourceDoc= builder.parse(file.getInputStream());

            //Create a new document for the target format
            Document targetDoc=builder.newDocument();
//            Element targetRootElement= targetDoc.createElement(targetFormat);
//            targetDoc.appendChild(targetRootElement);

            //Create AEM Specific root element
            Element aemRootElement= targetDoc.createElement("jcr:root");
            aemRootElement.setAttribute("xmlns:cq","http://www.day.com/jcr/cq/1.0");
            aemRootElement.setAttribute("xmlns:jcr","http://www.jcp.org/jcr/1.0");
            aemRootElement.setAttribute("jcr:primaryType","cq:Page");
            targetDoc.appendChild(aemRootElement);

            //Create page content element
            Element contentElement=targetDoc.createElement("jcr:content");
            contentElement.setAttribute("jcr:primaryType","cq:PageContent");
            aemRootElement.appendChild(contentElement);

//            //Map source XML elements to AEM compatible elements
//            Element sourceRootElement= doc.getDocumentElement();
//            Element aemContentElement= targetDoc.createElement("cq:PageContent");
//            aemContentElement.setAttribute("jcr:primaryType","nt:unstructured");
//            aemRootElement.appendChild(aemContentElement);



            //TODO Mapping to be done
            NodeList sourceNodes=sourceDoc.getDocumentElement().getChildNodes();
            for (int i=0; i< sourceNodes.getLength();i++){
                if (sourceNodes.item(i).getNodeType()== Node.ELEMENT_NODE){
                    Element sourceElement= (Element) sourceNodes.item(i);
                    String tagName= sourceElement.getTagName();
                    String textContent= sourceElement.getTextContent();

                    //Map title, Content, author, publicationDate
                    if (tagName.equals("title") || tagName.equals("content") ||  tagName.equals("author") ||
                            tagName.equals("publicationDate")){
                        contentElement.setAttribute("jcr:"+ tagName,textContent);
                    } //Map tags
                    else if (tagName.equals("tags")) {
                        Element tagsElement= targetDoc.createElement("tags");
                        NodeList tagNodes=sourceElement.getChildNodes();
                        for (int j=0; j<tagNodes.getLength(); j++){
                            if (tagNodes.item(j).getNodeType()==Node.ELEMENT_NODE){
                                Element tagElement= (Element) tagNodes.item(j);
                                Element tagItemElement=targetDoc.createElement("items");
                                tagItemElement.setAttribute("jcr:primaryType","cq:Tag");
                                tagItemElement.setAttribute("jcr:title", tagElement.getTextContent());
                                tagItemElement.setAttribute("cq:tagID", tagElement.getTextContent().toLowerCase());
                                tagElement.appendChild(tagItemElement);
                            }

                    }
                        contentElement.appendChild(tagsElement);
                }



                }
            }

//            Save the AEM compatible file
            String downloadFolderPath= System.getProperty("user.home")+"/Downloads/Target_AEM";
            Path outputPath= Paths.get(downloadFolderPath, "target_aem.xml");
            Files.createDirectories(outputPath.getParent());
            File outputFile= outputPath.toFile();

            // If the file already exists, rename it
            if (outputFile.exists()) {
                int count = 1;
                String baseFileName = "target_aem";
                String extension = ".xml";
                while (outputFile.exists()) {
                    outputPath = Paths.get(downloadFolderPath, baseFileName + "_" + count + extension);
                    outputFile = outputPath.toFile();
                    count++;
                }
            }
//                    File outputFile=fileSavingService.saveToFile();

                    //Write Target XML to a file
                    TransformerFactory transformerFactory= TransformerFactory.newInstance();
                    Transformer transformer= transformerFactory.newTransformer();
                    DOMSource source= new DOMSource(targetDoc);
                    StreamResult result= new StreamResult(outputFile);
                    transformer.transform(source,result);
                    return "Conversion successful! Target file saved as target.xml";
        } catch (Exception e) {
            e.printStackTrace();
            return "Conversion Failed! Error: "+ e.getMessage();
        }
    }
}
