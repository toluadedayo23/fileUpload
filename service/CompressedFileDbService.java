package com.example.fileupload.service;

import com.example.fileupload.message.ResponseFile;
import com.example.fileupload.model.CompressedFileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompressedFileDbService {

    CompressedFileDB storeCompressed(MultipartFile file) throws IOException;

    CompressedFileDB getFileCompressed(Long fileId);

    List<ResponseFile> getAllFilesAndDisplayCompressed();

    List<ResponseFile> getAllFilesAndDownloadCompressed();
}
