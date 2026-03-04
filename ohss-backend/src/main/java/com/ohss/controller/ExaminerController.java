package com.ohss.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.ohss.repository.ExaminerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ExaminerController {


    @Autowired
    private ExaminerRepository examinerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/examiners")
    public List<Examiner> getAllExaminers() {
        return examinerRepository.findAll();
    }

    @PostMapping("/examiners")
    public Examiner createExaminer(@RequestBody Examiner examiner) {
        if (examiner.getPassword() != null && !examiner.getPassword().isEmpty()) {
            examiner.setPassword(passwordEncoder.encode(examiner.getPassword()));
        }
        return examinerRepository.save(examiner);
    }

    @GetMapping("/examiners/{id}")
    public ResponseEntity<Examiner> getExaminerById(@PathVariable Long id) {
        Examiner examiner = examinerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Examiner not found with id: " + id));
        return ResponseEntity.ok(examiner);
    }

    @PutMapping("/examiners/{id}")    
    public ResponseEntity<Examiner> updateExaminer(@PathVariable Long id, @RequestBody Examiner examinerDetails) {
        Examiner examiner = examinerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Examiner not found with id: " + id));
        examiner.setFirstName(examinerDetails.getFirstName());
        examiner.setLastName(examinerDetails.getLastName());
        examiner.setEmail(examinerDetails.getEmail());
        examiner.setPhone(examinerDetails.getPhone());
        
        // Only update password if a new one is provided
        if (examinerDetails.getPassword() != null && !examinerDetails.getPassword().isEmpty()) {
            examiner.setPassword(passwordEncoder.encode(examinerDetails.getPassword()));
        }
        
        Examiner updatedExaminer = examinerRepository.save(examiner);
        return ResponseEntity.ok(updatedExaminer);
    }

    @DeleteMapping("/examiners/{id}")
    public ResponseEntity<Void> deleteExaminer(@PathVariable Long id) {
        Examiner examiner = examinerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Examiner not found with id: " + id));
        examinerRepository.delete(examiner);
        return ResponseEntity.noContent().build();  
    }
}
