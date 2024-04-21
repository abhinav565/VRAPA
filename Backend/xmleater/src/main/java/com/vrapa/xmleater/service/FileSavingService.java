package com.vrapa.xmleater.service;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSavingService {

    public String saveToFile(String xmlContent) {
        try {
            // Define the download folder path
            String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";

            // Create the output file path
            Path outputPath = Paths.get(downloadFolderPath, "target_aem.xml");

            // Check if the output file already exists
            int count = 1;
            while (Files.exists(outputPath)) {
                outputPath = Paths.get(downloadFolderPath, "target_aem_" + count + ".xml");
                count++;
            }

            // Write the XML content to the output file
            File outputFile = outputPath.toFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(xmlContent);
            fileWriter.close();

            return "File saved successfully as: " + outputFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to save file: " + e.getMessage();
        }
    }
}

