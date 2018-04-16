package com.massimo.webapp.service;

import com.massimo.webapp.model.Skill;
import com.massimo.webapp.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;
    @Override
    public List<Skill> findAllSkills() {
        List<Skill> skillList = (List<Skill>) skillRepository.findAll();
        return skillList;
    }

    @Override
    public Skill findById(int id) {
        Skill skill = skillRepository.findOne(id);

        return skill;
    }

}
