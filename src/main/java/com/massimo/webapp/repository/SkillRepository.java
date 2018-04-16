package com.massimo.webapp.repository;

import com.massimo.webapp.model.Skill;
import com.massimo.webapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer> {
}
