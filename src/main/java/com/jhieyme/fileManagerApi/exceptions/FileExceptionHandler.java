package com.jhieyme.fileManagerApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.jhieyme.fileManagerApi.constants.MessageConst.*;

@ControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> maxSizeException(MaxUploadSizeExceededException exception) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(MESSAGE_EXCEEDS_SIZE );
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> fileNotFoundException(FileNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MESSAGE_FILE_NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> ioException(IOException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MESSAGE_FILE_PROCESS_ERROR);
    }

}