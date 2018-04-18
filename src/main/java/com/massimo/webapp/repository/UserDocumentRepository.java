package com.massimo.webapp.repository;

import com.massimo.webapp.model.User;
import com.massimo.webapp.model.UserDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDocumentRepository extends CrudRepository<UserDocument, Integer> {

    List<UserDocument> findAllByUser(int userId);
    UserDocument findByUser_Id(int userId);
}
