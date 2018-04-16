package com.massimo.webapp.service;

import com.massimo.webapp.model.MaritalStatus;
import com.massimo.webapp.repository.MaritalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaritalStatusServiceImpl implements MaritalStatusService{

    @Autowired
    MaritalStatusRepository maritalStatusRepository;


    @Override
    @Transactional
    public List<MaritalStatus> findAllMaritalStatus() {
        List<MaritalStatus> maritalStatusList = (List<MaritalStatus>)maritalStatusRepository.findAll();
        return maritalStatusList;
    }


}
