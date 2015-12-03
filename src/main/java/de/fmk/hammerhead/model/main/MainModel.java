package de.fmk.hammerhead.model.main;

import de.fmk.hammerhead.common.ImportException;
import de.fmk.hammerhead.db.DBHandler;
import de.fmk.hammerhead.db.ImportHelper;
import de.fmk.hammerhead.exercise.db.EXERCISE_DB_CONSTANTS;
import de.fmk.hammerhead.common.xml.Entry;
import de.fmk.hammerhead.common.xml.XMLDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Fabian on 13.11.2015.
 */
public class MainModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainModel.class);


    public void initDatabase(String url, String name, String password) {
        try {
            DBHandler.INSTANCE.initConnection(url, name, password);

            _initExerciseTables();
            _importExerciseData("/data/musclegroups.xml", EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS);
            _importExerciseData("/data/muscles.xml", EXERCISE_DB_CONSTANTS.TBL_MUSCLES);
            _importExerciseData("/data/exercises.xml", EXERCISE_DB_CONSTANTS.TBL_EXERCISES);
            _importExerciseData("/data/exercise_muscles.xml", EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE);
        }
        catch (SQLException sqlex) {
            LOGGER.error("An error occured while initializing the database.", sqlex);
        }
    }


    private void _initExerciseTables() {
        boolean success;

        if (!DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS, false)) {
            success = DBHandler.INSTANCE.createTable(EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS,
                                                     EXERCISE_DB_CONSTANTS.CREATE_MUSCLEGROUPS_QUERY);

            if (success)
                LOGGER.info("The table " + EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS + " was created.");
        }

        if (!DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_MUSCLES, false)) {
            success = DBHandler.INSTANCE.createTable(EXERCISE_DB_CONSTANTS.TBL_MUSCLES,
                                                     EXERCISE_DB_CONSTANTS.CREATE_MUSCLES_QUERY);

            if (success)
                LOGGER.info("The table " + EXERCISE_DB_CONSTANTS.TBL_MUSCLES + " was created.");
        }

        if (!DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_EXERCISES, false)) {
            success = DBHandler.INSTANCE.createTable(EXERCISE_DB_CONSTANTS.TBL_EXERCISES,
                                                     EXERCISE_DB_CONSTANTS.CREATE_EXERCISES_QUERY);

            if (success)
                LOGGER.info("The table " + EXERCISE_DB_CONSTANTS.TBL_EXERCISES + " was created.");
        }

        if (!DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE, false)) {
            success = DBHandler.INSTANCE.createTable(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE,
                                                     EXERCISE_DB_CONSTANTS.CREATE_EXERCISE_MUSCLE_QUERY);

            if (success)
                LOGGER.info("The table " + EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE + " was created.");
        }
    }


    private void _importExerciseData(String resPath, String tableName) {
        final ImportHelper helper = new ImportHelper();

        Path         file;
        List<Entry>  data;
        List<String> colNames;

        try {
            file     = Paths.get(getClass().getResource(resPath).toURI());
            data     = new XMLDataHandler(file).parse();
            colNames = DBHandler.INSTANCE.getColumnsOfTable(tableName);

            if (!DBHandler.INSTANCE.tableHasData(tableName))
                helper.importData(tableName, data, colNames);
        }
        catch (ImportException iex) {
            LOGGER.error("An error occurred while importing data to table " + tableName + ".", iex);
        }
        catch (URISyntaxException urisex) {
            LOGGER.error("Could not load data file at " + resPath + ".", urisex);
        }
        catch (Exception ex) {
            LOGGER.error("Could not parse XML file at " + resPath + ".", ex);
        }
    }
}
