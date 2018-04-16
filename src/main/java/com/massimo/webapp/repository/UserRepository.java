package com.massimo.webapp.repository;

import com.massimo.webapp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.firstname like %:key% or u.lastname LIKE %:key% or u.country LIKE %:key%")
    List<User> findByFirstnameOrLastnameOrCountryContaining(@Param("key") String searchString);



}
