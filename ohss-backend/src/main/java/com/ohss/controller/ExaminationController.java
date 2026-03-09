package com.ohss.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.ohss.model.Examination;
import com.ohss.model.Examiner;
import com.ohss.repository.ExaminationRepository;
import com.ohss.repository.ExaminerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ExaminationController {

    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private ExaminerRepository examinerRepository;

    @GetMapping("/examinations")
    public List<Examination> getAllExaminations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        Examiner examiner = examinerRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with email: " + email));
        return examinationRepository.findByExaminerId(examiner.getId());
    
    }

    @PostMapping("/examinations")
    public Examination createExamination(@RequestBody Examination examination) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        Examiner examiner = examinerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Examiner not found with email: " + email));
        examination.setExaminer(examiner);

        return examinationRepository.save(examination);
    }

    @GetMapping("/examinations/{id}")
    public ResponseEntity<Examination> getExaminationById(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Examiner loggedInExaminer = examinerRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with email: " + email));
        Examination examination = examinationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Examination not found with id: " + id));
        if (!examination.getExaminer().getId().equals(loggedInExaminer.getId())) {
            throw new IllegalArgumentException("Examiners can only access their own examinations");
        }
        return ResponseEntity.ok(examination);
    }

    @GetMapping("/examinations/session/{sessionId}")
    public ResponseEntity<List<Examination>> getExaminationsBySessionId(@PathVariable Long sessionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Examiner loggedInExaminer = examinerRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with email: " + email));
        List<Examination> examinations = examinationRepository.findBySessionIdAndExaminerId(sessionId, loggedInExaminer.getId());
        if (examinations.isEmpty()) {
            throw new ResourceNotFoundException("No examinations of logged-in examiner found for session id: " + sessionId);
        }
        return ResponseEntity.ok(examinations);
    }


    @DeleteMapping("/examinations/{id}")
    public ResponseEntity<Void> deleteExamination(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Examiner loggedInExaminer = examinerRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Examiner not found with email: " + email));
        Examination examination = examinationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Examination not found with id: " + id));
        if (!examination.getExaminer().getId().equals(loggedInExaminer.getId())) {
            throw new IllegalArgumentException("Examiners can only delete their own examinations");
        }
        examinationRepository.delete(examination);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/examinations/{id}")
    public ResponseEntity<Examination> updateExamination(@PathVariable Long id, @RequestBody Examination examinationDetails) {
        if (!id.equals(examinationDetails.getId())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        Examination updatedExamination = examinationRepository.save(examinationDetails);
        return ResponseEntity.ok(updatedExamination);
    }
}