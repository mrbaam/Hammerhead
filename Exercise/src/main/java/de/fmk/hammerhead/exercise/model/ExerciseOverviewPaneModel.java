package de.fmk.hammerhead.exercise.model;

import de.fmk.hammerhead.common.GUID;
import de.fmk.hammerhead.db.DBHandler;
import de.fmk.hammerhead.db.QueryBuilder;
import de.fmk.hammerhead.db.value.PreparedBooleanValue;
import de.fmk.hammerhead.db.value.PreparedNullValue;
import de.fmk.hammerhead.db.value.PreparedStringValue;
import de.fmk.hammerhead.db.value.PreparedValue;
import de.fmk.hammerhead.exercise.data.Exercise;
import de.fmk.hammerhead.exercise.data.Muscle;
import de.fmk.hammerhead.exercise.data.MuscleGroup;
import de.fmk.hammerhead.exercise.db.EXERCISE_DB_CONSTANTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fabian on 16.11.2015.
 */
public class ExerciseOverviewPaneModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseOverviewPaneModel.class);

    private ObservableList<Exercise>    allExercises;
    private ObservableList<Exercise>    filteredExercises;
    private ObservableList<MuscleGroup> muscleGroups;
    private ObservableList<Muscle>      muscles;


    public ExerciseOverviewPaneModel() {
        allExercises      = FXCollections.observableArrayList();
        filteredExercises = FXCollections.observableArrayList();
        muscleGroups      = FXCollections.observableArrayList();
        muscles           = FXCollections.observableArrayList();
    }


    public void loadExercises() throws SQLException {
        final ResultSet result;

        Exercise exercise = null;

        result = DBHandler.INSTANCE.readTable(_buildReadAllExercisesQuery(), null);

        while (result.next()) {
            if (exercise == null || !exercise.getID().equals(result.getString("ID"))) {
                exercise = new Exercise(result.getString("ID"));

                allExercises.add(exercise);
            }

            if (exercise.getName() == null || !exercise.getName().equals(result.getString("NAME")))
                exercise.setName(result.getString("NAME"));

            exercise.setCustom(result.getBoolean("CUSTOM"));

            if (exercise.getMuscleGroup() == null) {
                final MuscleGroup mGrp = new MuscleGroup(result.getString("GRP_ID"));

                mGrp.setName(result.getString("MUSCLEGROUP"));
                exercise.setMuscleGroup(mGrp);
            }

            if (!exercise.containsTargetMuscle(result.getString("TARGET_ID"))) {
                final Muscle muscle = new Muscle(result.getString("TARGET_ID"));

                muscle.setName(result.getString("TARGET_MUSCLE"));
                exercise.addTargetMuscle(muscle);
            }

            if (!exercise.containsSupportMuscle(result.getString("SUPPORT_ID"))) {
                final Muscle muscle = new Muscle(result.getString("SUPPORT_ID"));

                muscle.setName(result.getString("SUPPORT_MUSCLE"));
                exercise.addSupportMuscle(muscle);
            }
        }

        filteredExercises.addAll(allExercises);

        DBHandler.INSTANCE.closeStatement();
    }

    /*
    SELECT EX.ID, EX.DE AS NAME, TARGET.ID AS TARGET_ID, TARGET.DE AS TARGET_MUSCLE, SUPPORT.ID AS SUPPORT_ID, SUPPORT.DE AS SUPPORT_MUSCLE, GRP.ID, GRP.DE
    FROM EXERCISES EX
    JOIN EXERCISE_MUSCLE EM1 ON EX.TARGET_MUSCLES=EM1.EXERCISE JOIN MUSCLES TARGET ON TARGET.ID=EM1.MUSCLE
    LEFT JOIN EXERCISE_MUSCLE EM2 ON EX.SUPPORT_MUSCLES=EM2.EXERCISE LEFT JOIN MUSCLES SUPPORT ON SUPPORT.ID=EM2.MUSCLE
    JOIN MUSCLEGROUPS GRP ON EX.MUSCLEGROUP=GRP.ID
    ORDER BY EX.DE
    */
    private String _buildReadAllExercisesQuery() {
        final StringBuilder queryBuilder;
        final String        language;

        language = Locale.getDefault().getLanguage().toUpperCase();

        queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT EX.ID, EX.").append(language).append(" AS NAME, ")
                    .append("EX.CUSTOM AS CUSTOM, ")
                    .append("TARGET.ID AS TARGET_ID, TARGET.").append(language).append(" AS TARGET_MUSCLE, ")
                    .append("SUPPORT.ID AS SUPPORT_ID, SUPPORT.").append(language).append(" AS SUPPORT_MUSCLE, ")
                    .append("GRP.ID AS GRP_ID, GRP.DE AS MUSCLEGROUP ");

        queryBuilder.append("FROM EXERCISES EX ");

        queryBuilder.append("JOIN EXERCISE_MUSCLE EM1 ON EX.TARGET_MUSCLES=EM1.EXERCISE ")
                        .append("JOIN MUSCLES TARGET ON TARGET.ID=EM1.MUSCLE ")
                    .append("LEFT JOIN EXERCISE_MUSCLE EM2 ON EX.SUPPORT_MUSCLES=EM2.EXERCISE ")
                        .append("LEFT JOIN MUSCLES SUPPORT ON SUPPORT.ID=EM2.MUSCLE ")
                    .append("JOIN MUSCLEGROUPS GRP ON EX.MUSCLEGROUP=GRP.ID ");

        queryBuilder.append("ORDER BY EX.").append(language).append(";");

        return queryBuilder.toString();
    }


    public void loadMuscles() throws SQLException {
        final ResultSet result;

        Muscle muscle = null;

        result = DBHandler.INSTANCE.readTable(_buildReadAllMusclesQuery(), null);

        while (result.next()) {
            if (muscle == null || !muscle.getID().equals(result.getString("ID"))) {
                muscle = new Muscle(result.getString("ID"));

                muscles.add(muscle);
            }

            if (muscle.getName() == null || !muscle.getName().equals(result.getString("NAME")))
                muscle.setName(result.getString("NAME"));

            if (muscle.getMuscleGroup() == null) {
                final MuscleGroup mGrp = new MuscleGroup(result.getString("GRP_ID"));

                mGrp.setName(result.getString("MUSCLEGROUP"));
                muscle.setMuscleGroup(mGrp);
            }
        }
    }


    /*
    SELECT M.ID AS ID, M.DE AS NAME, MG.ID AS GRP_ID, MG.DE AS MUSCLEGROUP
    FROM MUSCLES M
    JOIN MUSCLEGROUPS MG ON M.MUSCLEGROUP=MG.ID
    ORDER BY M.DE;
     */
    private String _buildReadAllMusclesQuery() {
        final StringBuilder queryBuilder = new StringBuilder();
        final String        language;

        language = Locale.getDefault().getLanguage().toUpperCase();

        queryBuilder.append("SELECT M.ID AS ID, M.").append(language).append(" AS NAME, ")
                    .append("MG.ID AS GRP_ID, MG.").append(language).append(" AS MUSCLEGROUP ")
                    .append("FROM MUSCLES M ")
                    .append("JOIN MUSCLEGROUPS MG ON M.MUSCLEGROUP=MG.ID ")
                    .append("ORDER BY M.").append(language).append(";");

        return queryBuilder.toString();
    }


    public void changeExercises(String searchText, MuscleGroup group) {
        filteredExercises.clear();

        if ((searchText == null || searchText.isEmpty()) && group == null) {
            filteredExercises.addAll(allExercises);
            return;
        }

        for (Exercise exercise : allExercises) {
            if (_matchesName(searchText, exercise.getName())) {
                if (group != null && exercise.getMuscleGroup().getID().equals(group.getID()))
                    filteredExercises.add(exercise);
                else if (group == null)
                    filteredExercises.add(exercise);
            }
        }
    }


    private boolean _matchesName(String searchString, String name) {
        final String regEx;

        if (searchString == null || searchString.isEmpty())
            return true;

        regEx = searchString.replaceAll("\\*", ".*").concat(".*").toLowerCase();

        return name.toLowerCase().matches(regEx);
    }


    public void loadMuscleGroups() throws SQLException {
        final ResultSet result;

        result = DBHandler.INSTANCE.readTable(_buildReadAllMuscleGroupsQuery(), null);

        while (result.next()) {
            final MuscleGroup grp;

            grp = new MuscleGroup(result.getString("ID"));
            grp.setName(result.getString("NAME"));

            muscleGroups.add(grp);
        }

        DBHandler.INSTANCE.closeStatement();

        FXCollections.sort(muscleGroups, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }


    private String _buildReadAllMuscleGroupsQuery() {
        final StringBuilder queryBuilder;
        final String        language;

        language = Locale.getDefault().getLanguage().toUpperCase();

        queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ID, ").append(language).append(" AS NAME ")
                    .append("FROM ").append(EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS).append(";");

        return queryBuilder.toString();
    }


    public boolean addExercise(Exercise exercise) {
        return _addOrEditExercise(exercise, true);
    }

    public boolean editExercise(Exercise exercise) {
        return _addOrEditExercise(exercise, false);
    }

    private boolean _addOrEditExercise(Exercise exercise, boolean add) {
        List<PreparedValue> values;
        List<String>        columns;
        String              query;

        columns = DBHandler.INSTANCE.getColumnsOfTable(EXERCISE_DB_CONSTANTS.TBL_EXERCISES);
        query   = QueryBuilder.createInsertOrReplaceQuery(EXERCISE_DB_CONSTANTS.TBL_EXERCISES,
                                                          columns, QueryBuilder.REPLACE);
        values  = _createExerciseValues(columns, exercise);

        if (DBHandler.INSTANCE.insertIntoTable(query, values) == -1) {
            LOGGER.warn("Could not save the exercise '" + exercise.getName() + "'.");
            return false;
        }

        for (Muscle muscle : exercise.getTargetMuscles())
            _addOrEditEntryInExerciseMuscleTable(exercise.getTargetID(), muscle.getID());

        for (Muscle muscle : exercise.getSupportMuscles())
            _addOrEditEntryInExerciseMuscleTable(exercise.getSupportID(), muscle.getID());

        if (add)
            allExercises.add(exercise);

        FXCollections.sort(allExercises, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        return true;
    }


    private List<PreparedValue> _createExerciseValues(List<String> columns, Exercise exercise) {
        final List<PreparedValue> values = new ArrayList<>();

        for (String colName : columns) {
            final int idx = columns.indexOf(colName) + 1;

            switch (colName) {
                case EXERCISE_DB_CONSTANTS.COL_ID:
                    values.add(new PreparedStringValue(idx, exercise.getID()));
                    break;

                case EXERCISE_DB_CONSTANTS.COL_MUSCLEGRP:
                    values.add(new PreparedStringValue(idx, exercise.getMuscleGroup().getID()));
                    break;

                case EXERCISE_DB_CONSTANTS.COL_TARGET:
                    exercise.setTargetID(GUID.createInstance(EXERCISE_DB_CONSTANTS.COL_TARGET).toString());
                    values.add(new PreparedStringValue(idx, exercise.getTargetID()));
                    break;

                case EXERCISE_DB_CONSTANTS.COL_SUPPORT:
                    if (!exercise.getSupportMuscles().isEmpty()) {
                        exercise.setSupportID(GUID.createInstance(EXERCISE_DB_CONSTANTS.COL_SUPPORT).toString());
                        values.add(new PreparedStringValue(idx, exercise.getSupportID()));
                    }
                    else {
                        values.add(new PreparedNullValue(idx, Types.VARCHAR));
                    }
                    break;

                case EXERCISE_DB_CONSTANTS.COL_CUSTOM:
                    values.add(new PreparedBooleanValue(idx, exercise.isCustom()));
                    break;

                default:
                    final Locale locale = new Locale(colName.toLowerCase());

                    if (locale.getISO3Language() != null && locale.getISO3Country() != null) {
                        values.add(new PreparedStringValue(idx, exercise.getName(colName)));
                        break;
                    }
                    else {
                        throw new IllegalArgumentException("'" + colName + "' is not a know column name of the table " +
                                EXERCISE_DB_CONSTANTS.TBL_EXERCISES + ".");
                    }
            }
        }

        return values;
    }


    private int _addOrEditEntryInExerciseMuscleTable(String linkExercise, String linkMuscle) {
        final List<PreparedValue> values;
        final String              query;

        values = new ArrayList<>();
        values.add(new PreparedStringValue(1, linkExercise));
        values.add(new PreparedStringValue(2, linkMuscle));

        query = QueryBuilder.createInsertOrReplaceQuery(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE,
                    DBHandler.INSTANCE.getColumnsOfTable(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE),
                    QueryBuilder.REPLACE);

        return DBHandler.INSTANCE.insertIntoTable(query, values);
    }


    public boolean deleteExercise(Exercise exercise) {
        String              query;
        List<String>        columns;
        List<PreparedValue> values;

        columns = new ArrayList<>();
        columns.add(EXERCISE_DB_CONSTANTS.COL_ID);

        query = QueryBuilder.createDeleteQuery(EXERCISE_DB_CONSTANTS.TBL_EXERCISES, columns);

        values = new ArrayList<>();
        values.add(new PreparedStringValue(1, exercise.getID()));

        if (DBHandler.INSTANCE.deleteFromTable(query, values) == -1) {
            LOGGER.warn("Could not delete the exercise '" + exercise.getName() + "'.");
            return false;
        }

        _deleteEntryFromExerciseMuscleTable(exercise.getTargetID());
        _deleteEntryFromExerciseMuscleTable(exercise.getSupportID());

        allExercises.remove(exercise);
        FXCollections.sort(allExercises, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        return true;
    }

    private void _deleteEntryFromExerciseMuscleTable(String id) {
        final List<String>        columns;
        final List<PreparedValue> values;
        final String              query;

        columns = new ArrayList<>();
        columns.add(EXERCISE_DB_CONSTANTS.COL_EXERCISE);

        query = QueryBuilder.createDeleteQuery(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE, columns);

        values = new ArrayList<>();
        values.add(new PreparedStringValue(1, id));

        DBHandler.INSTANCE.deleteFromTable(query, values);
    }


    public ObservableList<Exercise> getExercises() {
        return filteredExercises;
    }


    public ObservableList<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }


    public ObservableList<Muscle> getMuscles() {
        return muscles;
    }
}
