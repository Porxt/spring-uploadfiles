package com.softwareallies.uploadfiles.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.softwareallies.uploadfiles.domain.File;
import com.softwareallies.uploadfiles.storage.StorageService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/files")
public class FileUploadControllerRest {

    private final StorageService storageService;

    public FileUploadControllerRest(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public List<File> listUploadedFiles() {
        List<File> uploadedFiles = storageService.loadAll().map(path -> {
            String uri = MvcUriComponentsBuilder.fromMethodName(
                FileUploadController.class,
                "serveFile",
                path.getFileName().toString()
            ).build().toUri().toString();

            return new File(path.getFileName().toString(), uri);
        }).collect(Collectors.toList());

        return uploadedFiles;
    }

    @PostMapping
    public File uploadFile(@RequestParam("file") MultipartFile file) {
        String uri;

        storageService.store(file);

        uri = MvcUriComponentsBuilder.fromMethodName(
            FileUploadController.class,
            "serveFile",
            file.getOriginalFilename()
        ).build().toUri().toString();

        return new File(file.getOriginalFilename(), uri);
    }
}
