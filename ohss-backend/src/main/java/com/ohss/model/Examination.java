package com.ohss.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "examination")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
    
    @ManyToOne
    @JoinColumn(name = "examiner_id", nullable = false)
    private Examiner examiner;
    

    /* Profile Information */
    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "gender")
    private String gender;

    @Column(name = "smoking_status")
    private String smokingStatus;

    @Column(name = "alcohol_consumption")
    private String alcoholConsumption;

    @Column(name = "brushing_frequency")
    private String brushingFrequency;

    @Column(name = "last_visit")
    private Date lastVisit;

    /* Medical History */
    @Column(name = "diabetes")
    private Boolean diabetes;

    @Column(name = "cardiovascular_disease")
    private Boolean cardiovascularDisease;


    /* Examination Results */

    @Column(name = "dt")
    private Integer dt;

    @Column(name = "mt")
    private Integer mt; 

    @Column(name = "ft")
    private Integer ft;

    @Column(name = "cpi")
    private Integer cpi;



    public Examination() {
    }

    public Examination(Examiner examiner, Session session, Integer cpi, Integer dt, Integer mt, Integer ft, Date lastVisit) {
        this.examiner = examiner;
        this.session = session;
        this.cpi = cpi;
        this.dt = dt;
        this.mt = mt;
        this.ft = ft;
        this.lastVisit = lastVisit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }

    public Examiner getExaminer() {
        return examiner;
    }

    public void setExaminer(Examiner examiner) {
        this.examiner = examiner;
    }

    public Integer getCpi() {
        return cpi;
    }

    public void setCpi(Integer cpi) {
        this.cpi = cpi;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }   

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public String getAlcoholConsumption() {
        return alcoholConsumption;
    }

    public void setAlcoholConsumption(String alcoholConsumption) {
        this.alcoholConsumption = alcoholConsumption;
    }

    public String getBrushingFrequency() {
        return brushingFrequency;
    }

    public void setBrushingFrequency(String brushingFrequency) {
        this.brushingFrequency = brushingFrequency;
    }

    public Boolean getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Boolean diabetes) {
        this.diabetes = diabetes;
    }

    public Boolean getCardiovascularDisease() {
        return cardiovascularDisease;
    }

    public void setCardiovascularDisease(Boolean cardiovascularDisease) {
        this.cardiovascularDisease = cardiovascularDisease;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Integer getMt() {
        return mt;
    }

    public void setMt(Integer mt) {
        this.mt = mt;
    }

    public Integer getFt() {
        return ft;
    }

    public void setFt(Integer ft) {
        this.ft = ft;
    }

}

    