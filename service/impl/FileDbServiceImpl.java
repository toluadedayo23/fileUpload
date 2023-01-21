package com.example.fileupload.service.impl;

import com.example.fileupload.Exception.FileException;
import com.example.fileupload.message.ResponseFile;
import com.example.fileupload.model.FileDB;
import com.example.fileupload.repository.FileDBRepository;
import com.example.fileupload.service.FileDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileDbServiceImpl implements FileDbService {

    private final FileDBRepository fileDBRepository;

    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!file.isEmpty()) {
            FileDB FileDB = new FileDB(null, fileName, file.getContentType(), file.getBytes());
            return fileDBRepository.save(FileDB);
        }

        throw new FileException("File: "+file.getOriginalFilename() +" is empty ");
}

    @Override
    public FileDB getFile(Long fileId) {

        FileDB fileDB = fileDBRepository.findById(fileId).orElseThrow(
                () -> new FileException("File with the ID: " + fileId + "not found"));
//        String fileDownload = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/files")
//                .path(String.valueOf(fileDB.getData().length))
//                .toUriString();
        return fileDB;
    }

    @Override
    public List<ResponseFile> getAllFilesAndDisplay() {
        List<FileDB> fileDBList = fileDBRepository.findAll();
        return fileDBList.stream()
                .map(fileDB ->
                {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/")
                            .path("display/file/")
                            .path(String.valueOf(fileDB.getId()))
                            .toUriString();

                    return new ResponseFile(fileDB.getName(),
                            fileDownloadUri,
                            fileDB.getType(),
                            fileDB.getData().length);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseFile> getAllFilesAndDownload() {
        List<FileDB> fileDBList = fileDBRepository.findAll();
        return fileDBList.stream()
                .map(fileDB ->
                {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/")
                            .path("download/file/")
                            .path(String.valueOf(fileDB.getId()))
                            .toUriString();

                    return new ResponseFile(fileDB.getName(),
                            fileDownloadUri,
                            fileDB.getType(),
                            fileDB.getData().length);
                })
                .collect(Collectors.toList());
    }
}
