package com.massimo.webapp.model;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {

    private MultipartFile file;

    private String description;

    public FileBucket() {
    }

    public FileBucket(MultipartFile file, String description) {
        this.file = file;
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
