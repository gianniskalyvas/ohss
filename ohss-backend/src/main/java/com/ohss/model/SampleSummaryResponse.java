package com.ohss.model;

import java.util.Map;

public class SampleSummaryResponse {

    public long totalExaminations;
    public long totalExaminers;
    public long totalSessions;

    public long genderMaleCount;
    public long genderFemaleCount;

    public Map<String, Long> ageGroupCounts;

}
