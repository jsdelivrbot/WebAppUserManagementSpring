package com.massimo.webapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "MARITALSTATUS")
public class MaritalStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maritalstatus_id", nullable = false)
    private Integer id;

    @Column(name = "marital_name", nullable = false)
    private String maritalname;



    public MaritalStatus(String maritalname) {
        this.maritalname = maritalname;
    }

    public MaritalStatus() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaritalname() {
        return maritalname;
    }

    public void setMaritalname(String maritalname) {
        this.maritalname = maritalname;
    }
}
