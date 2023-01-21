package com.example.fileupload.controller;

import com.example.fileupload.message.ResponseFile;
import com.example.fileupload.message.ResponseMessage;
import com.example.fileupload.model.FileDB;
import com.example.fileupload.service.FileDbService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class FileController {

    private final FileDbService fileDbService;

    @PostMapping("upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        String message = "";
        try {
            fileDbService.store(file);
            message = "Uploaded the file: " + file.getOriginalFilename() + " successfully";
            return ResponseEntity.ok().body(new ResponseMessage(message));
        } catch (IOException e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!!!";
            return ResponseEntity.badRequest().body(new ResponseMessage(message));
        }
    }

    @GetMapping("download/file/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long fileId){
        FileDB fileDB = fileDbService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileDB.getName()+ "\"")
                .body(fileDB.getData());
    }

    @GetMapping("display/file/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable("id") Long fileId){
        FileDB fileDB = fileDbService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileDB.getType()))
                .body(fileDB.getData());
    }


    @GetMapping("displayFiles")
    public ResponseEntity<List<ResponseFile>> getAllFilesAndDisplay(){
        return ResponseEntity.ok()
                .body(fileDbService.getAllFilesAndDisplay());
    }

    @GetMapping("downloadFiles")
    public ResponseEntity<List<ResponseFile>> getAllFilesAndDownload(){
        return ResponseEntity.ok()
                .body(fileDbService.getAllFilesAndDownload());
    }
}
