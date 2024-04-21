package com.vrapa.xmleater.service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.vrapa.xmleater.dto.TagObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.List;


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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.XMLFormatter;

@Service
public class XMLProcessServiceLifeRay {

    public String convertXml(MultipartFile file ){
        try {
            File inputFile = new File(System.getProperty("java.io.tmpdir") + file.getOriginalFilename());// Specify the path to your XML file
            file.transferTo(inputFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("item");

            ArrayList<TagObject> tagObjects=new ArrayList<>();
            String strfile = "src/main/resources/template/33121.xml";
            List<String> lines = Files.readAllLines(Paths.get(strfile), StandardCharsets.UTF_8);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Map <String, String> data=new HashMap<>();
                    List<String> modifiedLines = new java.util.ArrayList<>();
                    for (String line : lines) {
                        String modifiedLine = line.replace("firstParameter", eElement.getElementsByTagName("title").item(0).getTextContent()).replace("secondParameter", eElement.getElementsByTagName("link").item(0).getTextContent()).replace("thirdParameter", eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                        modifiedLines.add(modifiedLine);
                    }
                    Files.write(Paths.get("src/main/resources/group/20117/com.liferay.blogs.model.BlogsEntry/33121.xml"), modifiedLines, StandardCharsets.UTF_8);
                }
                System.out.println("File has been modified successfully.");
                String[] sourceDirectoryPaths = {"src/main/resources/group","src/main/resources/company","src/main/resources/manifest.xml"};
                String zipFilePath = "src/main/resources/lar/CopyBlogs.lar";
                try {
                    FileOutputStream fos = new FileOutputStream(zipFilePath);
                    ZipOutputStream zos = new ZipOutputStream(fos);

                    for (String sourceDirectory : sourceDirectoryPaths) {
                        File fileToZip = new File(sourceDirectory);
                        // Use the directory name as the base for entries in this part of the zip
                        zipFile(fileToZip, fileToZip.getName(), zos);
                    }

                    zos.close();
                    fos.close();

                    System.out.println("Zip file has been created successfully.");
                } catch (IOException e) {
                    System.out.println("Error creating zip file: " + e);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ConvertXML Function call ended";
}
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {

        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
                zos.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zos);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        fis.close();
    }

}
