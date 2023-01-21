package com.example.fileupload.service;

import com.example.fileupload.message.ResponseFile;
import com.example.fileupload.model.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileDbService {
    FileDB store(MultipartFile file) throws IOException;

    FileDB getFile(Long fileId);

    List<ResponseFile> getAllFilesAndDisplay();

    List<ResponseFile> getAllFilesAndDownload();
}
