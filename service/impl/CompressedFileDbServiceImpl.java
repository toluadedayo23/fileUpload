package com.example.fileupload.service.impl;

import com.example.fileupload.Exception.FileException;
import com.example.fileupload.message.ResponseFile;
import com.example.fileupload.model.CompressedFileDB;
import com.example.fileupload.repository.CompressedFileRepository;
import com.example.fileupload.service.CompressedFileDbService;
import com.example.fileupload.util.ImageUtil;
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
public class CompressedFileDbServiceImpl implements CompressedFileDbService {

    private final CompressedFileRepository compressedFileRepository;

    @Override
    public CompressedFileDB storeCompressed(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!file.isEmpty()) {
            CompressedFileDB FileDB = new CompressedFileDB(null, fileName, file.getContentType(), ImageUtil.compressFile(file.getBytes()));
            return compressedFileRepository.save(FileDB);
        }

        throw new FileException("File: " + file.getOriginalFilename() + " is empty ");
    }

    @Override
    public CompressedFileDB getFileCompressed(Long fileId) {

        CompressedFileDB fileDB = compressedFileRepository.findById(fileId).orElseThrow(
                () -> new FileException("File with the ID: " + fileId + "not found"));
//        String fileDownload = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/files")
//                .path(String.valueOf(fileDB.getData().length))
//                .toUriString();
        return fileDB;
    }

    @Override
    public List<ResponseFile> getAllFilesAndDisplayCompressed() {
        return getDbFileList().stream()
                .map(fileDB ->
                {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/apicompressed/")
                            .path("display/file/")
                            .path(String.valueOf(fileDB.getId()))
                            .toUriString();

                    byte [] decompressedFile = ImageUtil.decompressFile(fileDB.getData());

                    return new ResponseFile(fileDB.getName(),
                            fileDownloadUri,
                            fileDB.getType(),
                            fileDB.getData().length
//                            decompressedFile.length
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseFile> getAllFilesAndDownloadCompressed() {
        return getDbFileList().stream()
                .map(fileDB ->
                {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/apicompressed/")
                            .path("download/file/")
                            .path(String.valueOf(fileDB.getId()))
                            .toUriString();

                    byte [] decompressedFile = ImageUtil.decompressFile(fileDB.getData());

                    return new ResponseFile(fileDB.getName(),
                            fileDownloadUri,
                            fileDB.getType(),
                            fileDB.getData().length
//                            decompressedFile.length
                            );
                })
                .collect(Collectors.toList());
    }

    private List<CompressedFileDB> getDbFileList() {
        return compressedFileRepository.findAll();
    }
}

