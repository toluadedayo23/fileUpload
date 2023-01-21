package com.example.fileupload.repository;

import com.example.fileupload.model.CompressedFileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompressedFileRepository extends JpaRepository<CompressedFileDB, Long> {
}
