package com.example.fileupload.Exception;

import lombok.Data;

@Data
public class FileException extends RuntimeException{
    private String message;

    public FileException(String message){
        super(message);
        this.message = message;
    }
}
