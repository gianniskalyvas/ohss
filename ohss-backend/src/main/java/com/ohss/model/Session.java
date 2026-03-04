package com.ohss.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "examiner_id", nullable = false)
    private Examiner leadExaminer;
    
    @Column(name = "school")
    private String school;

    @Column(name = "region")
    private String region;

    @Column(name = "date")
    private String date;


    public Session() {
    }

    public Session(String school, String region, String date, Examiner leadExaminer) {
        this.school = school;
        this.region = region;
        this.date = date;
        this.leadExaminer = leadExaminer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Examiner getLeadExaminer() {
        return leadExaminer;
    }

    public void setLeadExaminer(Examiner leadExaminer) {
        this.leadExaminer = leadExaminer;
    }
}