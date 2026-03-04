package com.ohss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohss.exception.ResourceNotFoundException;
import com.ohss.model.Examiner;
import com.ohss.model.Session;
import com.ohss.repository.ExaminerRepository;
import com.ohss.repository.SessionRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ExaminerRepository examinerRepository;

    @GetMapping("/sessions")
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @PostMapping("/sessions")
    public Session createSession(@RequestBody Session session) {
        if (session.getLeadExaminer() != null && session.getLeadExaminer().getId() != null) {
            Examiner examiner = examinerRepository.findById(session.getLeadExaminer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with id: " + session.getLeadExaminer().getId()));
            session.setLeadExaminer(examiner);
        }
        return sessionRepository.save(session);
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
        return ResponseEntity.ok(session);
    }

    @PutMapping("/sessions/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session sessionDetails) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
        
        session.setSchool(sessionDetails.getSchool());
        session.setRegion(sessionDetails.getRegion());
        session.setDate(sessionDetails.getDate());
        
        // Update lead examiner if provided
        if (sessionDetails.getLeadExaminer() != null && sessionDetails.getLeadExaminer().getId() != null) {
            Examiner examiner = examinerRepository.findById(sessionDetails.getLeadExaminer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with id: " + sessionDetails.getLeadExaminer().getId()));
            session.setLeadExaminer(examiner);
        }
        
        Session updatedSession = sessionRepository.save(session);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
        sessionRepository.delete(session);
        return ResponseEntity.noContent().build();
    }
}
