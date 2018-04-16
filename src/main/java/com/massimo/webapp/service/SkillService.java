package com.massimo.webapp.service;

import com.massimo.webapp.model.Skill;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface SkillService {


    List<Skill> findAllSkills();

    Skill findById(int id);


}
