package com.massimo.webapp.repository;

import com.massimo.webapp.model.MaritalStatus;
import com.massimo.webapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaritalStatusRepository extends CrudRepository<MaritalStatus, Integer> {
}
