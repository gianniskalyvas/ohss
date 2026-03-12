package com.ohss.model;

import java.util.List;

public class HabitsResponse {

    public static class NameValue {
        public String name;
        public Double value;

        public NameValue() {}
        public NameValue(String name, Double value) {
            this.name = name;
            this.value = value;
        }
    }

    // Grouped averages by SmokingStatus
    public List<NameValue> smokingStatusCPI;
    public List<NameValue> smokingStatusDMFT;

    // Grouped averages by AlcoholConsumption
    public List<NameValue> alcoholConsumptionCPI;
    public List<NameValue> alcoholConsumptionDMFT;

    // Grouped averages by BrushingFrequency
    public List<NameValue> brushingFrequencyCPI;
    public List<NameValue> brushingFrequencyDMFT;
}
