package com.vrapa.xmleater.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vrapa.xmleater.models.FileUploadResponse;
import com.vrapa.xmleater.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("xmlEater")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/uploadFile")
    public ResponseEntity<List<FileUploadResponse>> uploadFile(@RequestParam("file") List<MultipartFile> multipartFiles)
            throws IOException {

        List<FileUploadResponse> fileResponseList = new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            long size = multipartFile.getSize();
            String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/downloadFile/" + filecode);

            fileResponseList.add(response);
        }
        return new ResponseEntity<>(fileResponseList, HttpStatus.OK);
    }
}
