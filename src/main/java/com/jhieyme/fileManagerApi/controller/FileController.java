package com.jhieyme.fileManagerApi.controller;

import com.jhieyme.fileManagerApi.model.File;
import com.jhieyme.fileManagerApi.service.FileService;
import com.jhieyme.fileManagerApi.utils.ResponseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.jhieyme.fileManagerApi.constans.MessageConst.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/fileManager")
public class FileController {

    private final FileService fileService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            fileService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body(MESSAGE_OK);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(MESSAGE_EMPTY);
    }

    @GetMapping(path = "/files/{id}")
    public ResponseEntity<Object> getFile(@PathVariable UUID id) throws FileNotFoundException {
        File fileModel = fileService.downloadFile(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, fileModel.getType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "\"")
                .body(fileModel.getData());
    }

    @GetMapping(path = "/files")
    public ResponseEntity<Object> getFilesAll() {
        List<ResponseFile> files = fileService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @DeleteMapping(path = "/deleteFile/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable UUID id) throws FileNotFoundException {
        File fileFound = fileService.downloadFile(id).get();
        if (id != null && fileFound != null) {
            fileService.delete(fileFound);
            return ResponseEntity.status(HttpStatus.OK).body(MESSAGE_DELETE_OK);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(MESSAGE_DELETE_BAD);
    }

}