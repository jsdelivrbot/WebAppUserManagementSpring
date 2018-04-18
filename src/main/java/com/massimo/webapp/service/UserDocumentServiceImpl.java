package com.massimo.webapp.service;

import com.massimo.webapp.model.FileBucket;
import com.massimo.webapp.model.UserDocument;
import com.massimo.webapp.repository.UserDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserDocumentServiceImpl implements UserDocumentService{


    @Autowired
    UserDocumentRepository userDocumentRepository;

    @Override
    public UserDocument findById(int id) {
        UserDocument userDocument = userDocumentRepository.findOne(id);
        return userDocument;
    }

    @Override
    public List<UserDocument> findAll() {
        List<UserDocument> userDocuments = (List<UserDocument>) userDocumentRepository.findAll();
        return userDocuments;
    }

    @Override
    public List<UserDocument> findAllByUserId(int id) {
        return userDocumentRepository.findAllByUser(id);
    }

    @Override
    public void saveDocument(UserDocument document) {
        userDocumentRepository.save(document);
    }

    @Override
    public void deleteById(int id) {

        userDocumentRepository.delete(id);
    }

    @Override
    public void deleteDoc(UserDocument userDocument) {
        userDocumentRepository.delete(userDocument);
    }

    @Override
    public UserDocument findByUserId(int userId) {
        return userDocumentRepository.findByUser_Id(userId);
    }

    @Override
    public void updateDocument(UserDocument document, FileBucket fileBucket, MultipartFile file) throws IOException {
        UserDocument userDocument = userDocumentRepository.findOne(document.getId());
        if(userDocument!=null){


            userDocument.setContent(file.getBytes());
            userDocument.setDescription(fileBucket.getDescription());
            userDocument.setName(file.getOriginalFilename());
            userDocument.setType(file.getContentType());
            userDocument.setUser(userDocument.getUser());

            userDocumentRepository.save(userDocument);
        }
    }
}
