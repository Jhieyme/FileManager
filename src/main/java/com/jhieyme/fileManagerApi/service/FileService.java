package com.jhieyme.fileManagerApi.service;

import com.jhieyme.fileManagerApi.model.File;
import com.jhieyme.fileManagerApi.utils.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {

    File save(MultipartFile file) throws IOException;

    Optional<File> downloadFile (UUID id) throws FileNotFoundException;

    List<ResponseFile> findAll();

    void delete (File file);
}
