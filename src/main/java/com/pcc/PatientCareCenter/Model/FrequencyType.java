package com.pcc.PatientCareCenter.Model;

import java.util.Arrays;
import java.util.Optional;

public enum FrequencyType {
    BD("bd"),
    TDS("tds"),
    QDS("qds"),
    SIX_HOURLY("6 hourly"),
    DAILY("daily"),
    WEEKLY("weekly");

    private final String name;

    FrequencyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDailyFrequency() {
        return getDailyFrequency(this);
    }

    public static int getDailyFrequency(FrequencyType type) {
        int f = 0;
        switch (type) {
            case BD -> f = 2;
            case QDS, SIX_HOURLY -> f = 4;
            case TDS -> f = 3;
            case DAILY,WEEKLY -> f = 1;
        }
        return f;
    }

    public static FrequencyType getFrequencyType(int frq) {
        FrequencyType f = null;
        switch (frq) {
            case 2 -> f = BD;
            case 4 -> f = QDS;
            case 3 -> f = TDS;
            case 1 -> f = DAILY;
        }
        return f;
    }

    public static FrequencyType getFrequencyType(String frq) {
        Optional<FrequencyType> first = Arrays.stream(FrequencyType.values()).filter(frequencyType -> frequencyType.getName().equals(frq)).findFirst();
        return first.orElse(null);
    }
}
