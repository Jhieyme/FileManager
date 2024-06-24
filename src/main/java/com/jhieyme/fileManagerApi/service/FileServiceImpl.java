package com.jhieyme.fileManagerApi.service;

import com.jhieyme.fileManagerApi.model.File;
import com.jhieyme.fileManagerApi.repository.FileRepository;
import com.jhieyme.fileManagerApi.utils.ResponseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    @Override
    public File save(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileModel = File.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        return fileRepository.save(fileModel);
    }

    @Override
    public Optional<File> downloadFile(UUID id) throws FileNotFoundException {
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()){
            return file;
        }
        throw new FileNotFoundException();
    }

    @Override
    public List<ResponseFile> findAll() {
        List<ResponseFile> files = fileRepository.findAll().stream().map(dbFile ->{
            String fileDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/fileManager/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return ResponseFile.builder()
                    .id(dbFile.getId())
                    .name(dbFile.getName())
                    .url(fileDownload)
                    .type(dbFile.getType())
                    .size(dbFile.getData().length)
                    .build();
        }).collect(Collectors.toList());
        return files;
    }

    @Override
    public void delete(File file) {
        fileRepository.delete(file);
    }

}
