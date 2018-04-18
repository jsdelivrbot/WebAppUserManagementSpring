package com.massimo.webapp.service;

import com.massimo.webapp.model.FileBucket;
import com.massimo.webapp.model.UserDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserDocumentService {

    UserDocument findById(int id);

    List<UserDocument> findAll();

    List<UserDocument> findAllByUserId(int id);

    void saveDocument(UserDocument document);

    void deleteById(int id);

    void deleteDoc(UserDocument userDocument);

    UserDocument findByUserId(int userId);

    void updateDocument(UserDocument document, FileBucket fileBucket, MultipartFile multipartFile) throws IOException;
}
