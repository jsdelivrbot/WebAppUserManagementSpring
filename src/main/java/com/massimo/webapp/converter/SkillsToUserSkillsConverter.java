package com.massimo.webapp.converter;

import com.massimo.webapp.model.Skill;
import com.massimo.webapp.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SkillsToUserSkillsConverter implements Converter<Object, Skill> {

    @Autowired
    SkillService skillService;
    @Override
    public Skill convert(Object o) {
        Integer id = Integer.parseInt((String) o);
        Skill skill = skillService.findById(id);
        return skill;
    }
}
