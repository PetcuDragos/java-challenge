package ro.codecrafters.BankingSystem.util;

import java.util.Arrays;

public enum Risk {
    NO_RISK ("Candidate with no risk."),
    MEDIUM_RISK ("Candidate with medium risk, but enrollment still possible."),
    HIGH_RISK ("Risky candidate, enrollment not acceptable");

    public final String message;
    Risk(String message) {
        this.message = message;
    }

    public static Risk getEnumByValue(int riskValue) {
        if (riskValue <= 20) return NO_RISK;
        if (riskValue <= 99) return MEDIUM_RISK;
        return HIGH_RISK;
    }
}
