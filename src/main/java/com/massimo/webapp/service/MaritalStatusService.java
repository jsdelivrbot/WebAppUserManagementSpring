package com.massimo.webapp.service;

import com.massimo.webapp.model.MaritalStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MaritalStatusService {


    List<MaritalStatus> findAllMaritalStatus();

}
