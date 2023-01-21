package com.example.fileupload.repository;

import com.example.fileupload.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, Long> {

}
