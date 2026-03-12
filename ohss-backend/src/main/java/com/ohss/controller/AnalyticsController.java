package com.ohss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohss.model.Examination;
import com.ohss.model.HabitsResponse;
import com.ohss.model.RegionalResponse;
import com.ohss.model.SampleSummaryResponse;
import com.ohss.repository.ExaminationRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class AnalyticsController {

    @Autowired
    private ExaminationRepository examinationRepository;

    private List<Examination> getAllExaminations() {
        return examinationRepository.findAll();
    }


    @GetMapping("/analytics/summary")
    public ResponseEntity<SampleSummaryResponse> getSampleCharacteristics() {
        List<Examination> allExams = getAllExaminations();
        SampleSummaryResponse result = new SampleSummaryResponse();

        // Totals
        result.totalExaminations = allExams.size();
        result.totalExaminers = allExams.stream().map(exam -> exam.getExaminer().getId()).distinct().count();
        result.totalSessions = allExams.stream().map(exam -> exam.getSession().getId()).distinct().count();

        // Totals
        result.genderMaleCount = allExams.stream().filter(e -> "MALE".equalsIgnoreCase(e.getGender())).count();
        result.genderFemaleCount = allExams.stream().filter(e -> "FEMALE".equalsIgnoreCase(e.getGender())).count();

        // Age groups as a Map
        java.util.Map<String, Long> ageGroupCounts = new java.util.HashMap<>();
        ageGroupCounts.put("6-9", allExams.stream().filter(e -> e.getAge() >= 6 && e.getAge() <= 9).count());
        ageGroupCounts.put("9-12", allExams.stream().filter(e -> e.getAge() >= 9 && e.getAge() <= 12).count());
        ageGroupCounts.put("12-15", allExams.stream().filter(e -> e.getAge() >= 12 && e.getAge() <= 15).count());
        ageGroupCounts.put("15-18", allExams.stream().filter(e -> e.getAge() >= 15 && e.getAge() <= 18).count());
        result.ageGroupCounts = ageGroupCounts;

        return ResponseEntity.ok(result);
    }


    @GetMapping("/analytics/regions")
    public ResponseEntity<RegionalResponse> getRegionalData() {
        List<Examination> allExams = getAllExaminations();
        RegionalResponse result = new RegionalResponse();

        // Per-region averages for oral health metrics
        java.util.Map<String, Double> regionCpiAvg = allExams.stream()
            .filter(e -> e.getSession() != null && e.getSession().getRegion() != null && e.getCpi() != null)
            .collect(java.util.stream.Collectors.groupingBy(
                e -> e.getSession().getRegion(),
                java.util.stream.Collectors.averagingInt(Examination::getCpi)
            ));
        java.util.Map<String, Double> regionDtAvg = allExams.stream()
            .filter(e -> e.getSession() != null && e.getSession().getRegion() != null && e.getDt() != null)
            .collect(java.util.stream.Collectors.groupingBy(
                e -> e.getSession().getRegion(),
                java.util.stream.Collectors.averagingInt(Examination::getDt)
            ));
        java.util.Map<String, Double> regionMtAvg = allExams.stream()
            .filter(e -> e.getSession() != null && e.getSession().getRegion() != null && e.getMt() != null)
            .collect(java.util.stream.Collectors.groupingBy(
                e -> e.getSession().getRegion(),
                java.util.stream.Collectors.averagingInt(Examination::getMt)
            ));
        java.util.Map<String, Double> regionFtAvg = allExams.stream()
            .filter(e -> e.getSession() != null && e.getSession().getRegion() != null && e.getFt() != null)
            .collect(java.util.stream.Collectors.groupingBy(
                e -> e.getSession().getRegion(),
                java.util.stream.Collectors.averagingInt(Examination::getFt)
            ));

        result.getData().put("regionCPI", regionCpiAvg);
        // Sum regionDtAvg, regionMtAvg, and regionFtAvg per region for DMFT
        java.util.Map<String, Double> regionDMFT = new java.util.HashMap<>();
        java.util.Set<String> allRegions = new java.util.HashSet<>();
        allRegions.addAll(regionDtAvg.keySet());
        allRegions.addAll(regionMtAvg.keySet());
        allRegions.addAll(regionFtAvg.keySet());
        for (String region : allRegions) {
            double dt = regionDtAvg.getOrDefault(region, 0.0);
            double mt = regionMtAvg.getOrDefault(region, 0.0);
            double ft = regionFtAvg.getOrDefault(region, 0.0);
            regionDMFT.put(region, dt + mt + ft);
        }
        result.getData().put("regionDMFT", regionDMFT);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/analytics/habits")
    public ResponseEntity<HabitsResponse> getAnalytics() {


        List<Examination> allExams = getAllExaminations();
        HabitsResponse result = new HabitsResponse();



        // Grouped averages for oral health metrics by enums, ignoring UNKNOWN
        java.util.function.Function<String, Boolean> isKnown = v -> v != null && !v.equalsIgnoreCase("UNKNOWN");

        // Helper for grouping and averaging, ignoring nulls
        java.util.function.BiFunction<java.util.function.Function<Examination, String>, java.util.function.Function<Examination, Integer>, java.util.Map<String, Double>> groupAvg = (groupBy, valueFn) ->
            allExams.stream()
                .filter(e -> groupBy.apply(e) != null && isKnown.apply(groupBy.apply(e)))
                .collect(java.util.stream.Collectors.groupingBy(
                    groupBy,
                    java.util.stream.Collectors.averagingInt(e -> {
                        Integer v = valueFn.apply(e);
                        return v == null ? 0 : v;
                    })
                ));



        // Helper to convert Map<String, Double> to List<NameValue>
        java.util.function.Function<java.util.Map<String, Double>, java.util.List<HabitsResponse.NameValue>> mapToList = map ->
            map.entrySet().stream()
                .map(e -> new HabitsResponse.NameValue(e.getKey(), e.getValue()))
                .collect(java.util.stream.Collectors.toList());

        // SmokingStatus
        result.smokingStatusCPI = mapToList.apply(groupAvg.apply(e -> e.getSmokingStatus() != null ? e.getSmokingStatus().toString() : null, Examination::getCpi));
        result.smokingStatusDMFT = mapToList.apply(groupAvg.apply(e -> e.getSmokingStatus() != null ? e.getSmokingStatus().toString() : null, Examination::getDMFT));

        // AlcoholConsumption
        result.alcoholConsumptionCPI = mapToList.apply(groupAvg.apply(e -> e.getAlcoholConsumption() != null ? e.getAlcoholConsumption().toString() : null, Examination::getCpi));
        result.alcoholConsumptionDMFT = mapToList.apply(groupAvg.apply(e -> e.getAlcoholConsumption() != null ? e.getAlcoholConsumption().toString() : null, Examination::getDMFT));

        // BrushingFrequency
        result.brushingFrequencyCPI = mapToList.apply(groupAvg.apply(e -> e.getBrushingFrequency() != null ? e.getBrushingFrequency().toString() : null, Examination::getCpi));
        result.brushingFrequencyDMFT = mapToList.apply(groupAvg.apply(e -> e.getBrushingFrequency() != null ? e.getBrushingFrequency().toString() : null, Examination::getDMFT));


        return ResponseEntity.ok(result);
    }


}