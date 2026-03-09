package com.ohss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohss.model.Examination;
import com.ohss.repository.ExaminationRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/analytics/")
public class AnalyticsController {

    @Autowired
    private ExaminationRepository examinationRepository;


    @GetMapping("/")
    public ResponseEntity<String> getAnalytics() {

        List<Examination> allExams = examinationRepository.findAll();
        java.util.Map<String, Object> result = new java.util.HashMap<>();

        int totalExams = allExams.size();
        result.put("totalExaminations", totalExams);

        long totalExaminers = allExams.stream()
            .map(exam -> exam.getExaminer().getId())
            .distinct()
            .count();
        result.put("totalExaminers", totalExaminers);

        double meanCpi = allExams.stream()
            .filter(e -> e.getCpi() != null)
            .mapToInt(Examination::getCpi)
            .average().orElse(Double.NaN);
        result.put("meanCpi", meanCpi);

        double meanDt = allExams.stream()
            .filter(e -> e.getDt() != null)
            .mapToInt(Examination::getDt)
            .average().orElse(Double.NaN);
        result.put("meanDt", meanDt);

        double meanMt = allExams.stream()
            .filter(e -> e.getMt() != null)
            .mapToInt(Examination::getMt)
            .average().orElse(Double.NaN);
        result.put("meanMt", meanMt);

        double meanFt = allExams.stream()
            .filter(e -> e.getFt() != null)
            .mapToInt(Examination::getFt)
            .average().orElse(Double.NaN);
        result.put("meanFt", meanFt);

        // Count examinations where cardiovascularDisease is true
        long cvdCount = allExams.stream()
            .filter(e -> Boolean.TRUE.equals(e.getCardiovascularDisease()))
            .count();
        result.put("cardiovascularDiseaseCount", cvdCount);

        return ResponseEntity.ok(new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(result).toString());
    }
}
