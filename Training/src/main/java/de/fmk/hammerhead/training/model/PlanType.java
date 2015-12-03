package de.fmk.hammerhead.training.model;

/**
 * Created by Fabian on 03.12.2015.
 */
public enum PlanType {
    WHOLE_BODY("Ganzkörper", 1),
    TWO_DAY_SPLIT("2-Tage-Split", 2),
    THREE_DAY_SPLIT("3-Tage-Split", 3),
    FOUR_DAY_SPLIT("4-Tage-Split", 4),
    FIVE_DAY_SPLIT("5-Tage-Split", 5),
    SIX_DAY_SPLIT("6-Tage-Split", 6),
    SEVEN_DAY_SPLIT("7-Tage-Split", 7);


    private String type;
    private int    days;


    private PlanType(String type, int days) {
        this.type = type;
        this.days = days;
    }


    public String getText() {
        return type;
    }


    public int getDays() {
        return days;
    }
}
