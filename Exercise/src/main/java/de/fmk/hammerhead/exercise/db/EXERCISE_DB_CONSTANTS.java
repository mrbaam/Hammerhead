package de.fmk.hammerhead.exercise.db;

/**
 * Created by Fabian on 13.11.2015.
 */
public final class EXERCISE_DB_CONSTANTS {
    public static final String COL_CUSTOM    = "CUSTOM";
    public static final String COL_EXERCISE  = "EXERCISE";
    public static final String COL_ID        = "ID";
    public static final String COL_MUSCLE    = "MUSCLE";
    public static final String COL_MUSCLEGRP = "MUSCLEGROUP";
    public static final String COL_SUPPORT   = "SUPPORT_MUSCLES";
    public static final String COL_TARGET    = "TARGET_MUSCLES";

    public static final String TBL_EXERCISE_MUSCLE = "EXERCISE_MUSCLE";
    public static final String TBL_EXERCISES       = "EXERCISES";
    public static final String TBL_MUSCLEGROUPS    = "MUSCLEGROUPS";
    public static final String TBL_MUSCLES         = "MUSCLES";

    public static final String CREATE_EXERCISE_MUSCLE_QUERY = "CREATE TABLE IF NOT EXISTS " +
                                                              TBL_EXERCISE_MUSCLE +
                                                              "(" + COL_EXERCISE + " VARCHAR(36) NOT NULL, " +
                                                              COL_MUSCLE + " VARCHAR(36) NOT NULL, " +
                                                              "PRIMARY KEY (" + COL_EXERCISE + ", " + COL_MUSCLE + "));";

    public static final String CREATE_EXERCISES_QUERY = "CREATE TABLE IF NOT EXISTS " +
                                                        TBL_EXERCISES +
                                                        "(" + COL_ID + " VARCHAR(36) PRIMARY KEY NOT NULL, " +
                                                        COL_MUSCLEGRP + " VARCHAR(36) NOT NULL, " +
                                                        COL_TARGET + " VARCHAR(36) NOT NULL, " +
                                                        COL_SUPPORT + " VARCHAR(36), " +
                                                        COL_CUSTOM + " BOOLEAN, " +
                                                        "DE VARCHAR(255), " +
                                                        "EN VARCHAR(255));";

    public static final String CREATE_MUSCLEGROUPS_QUERY = "CREATE TABLE IF NOT EXISTS " +
                                                           TBL_MUSCLEGROUPS +
                                                           "(" + COL_ID + " VARCHAR(36) PRIMARY KEY NOT NULL, " +
                                                           "DE VARCHAR(255), " +
                                                           "EN VARCHAR(255));";

    public static final String CREATE_MUSCLES_QUERY = "CREATE TABLE IF NOT EXISTS " +
                                                      TBL_MUSCLES +
                                                      "(" + COL_ID + " VARCHAR(36) PRIMARY KEY NOT NULL, " +
                                                      COL_MUSCLEGRP + " VARCHAR(36) NOT NULL, " +
                                                      "DE VARCHAR(255), " +
                                                      "EN VARCHAR(255));";


    private EXERCISE_DB_CONSTANTS() {}
}
