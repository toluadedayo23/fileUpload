package com.example.fileupload.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileException.class)
    public ErrorResponse handleFileException(Exception e) {
        FileException fileException = (FileException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.error("File is empty");
        return new ErrorResponse(status, fileException.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponse handleMaxUploadSizeExceededException(Exception e) {
        MaxUploadSizeExceededException maxUploadSizeExceededException = (MaxUploadSizeExceededException) e;
        HttpStatus status = HttpStatus.REQUEST_ENTITY_TOO_LARGE;
        log.error("File is too large");
        return new ErrorResponse(status, maxUploadSizeExceededException.getMessage());
    }

//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        HttpStatus httpStatus = HttpStatus.valueOf(String.valueOf(status));
//        log.error("ile is missing, nothing to upload");
//        return new ResponseEntity<>(new ErrorResponse(httpStatus, ex.getMessage().toString()), status);
//    }
}
