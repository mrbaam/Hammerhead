package de.fmk.hammerhead.exercise.model;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.dialog.model.AbstractDialogPaneModel;
import de.fmk.hammerhead.db.DBHandler;
import de.fmk.hammerhead.exercise.data.Exercise;
import de.fmk.hammerhead.exercise.data.Muscle;
import de.fmk.hammerhead.exercise.data.MuscleGroup;
import de.fmk.hammerhead.exercise.db.EXERCISE_DB_CONSTANTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

/**
 * Created by Fabian on 21.11.2015.
 */
public class ExerciseEditPaneModel extends AbstractDialogPaneModel<Exercise> {
    private ObservableList<Muscle>      muscles;
    private ObservableList<MuscleGroup> muscleGroups;


    public ExerciseEditPaneModel(Exercise                    exercise,
                                 ObservableList<Muscle>      muscles,
                                 ObservableList<MuscleGroup> muscleGroups) {
        super(exercise);

        this.muscles      = FXCollections.observableArrayList(muscles);
        this.muscleGroups = FXCollections.observableArrayList(muscleGroups);

        object.setCustom(true);
    }


    public ObservableList<Muscle> getMuscles() {
        return FXCollections.observableArrayList(muscles);
    }


    public ObservableList<MuscleGroup> getMuscleGroups() {
        return FXCollections.observableArrayList(muscleGroups);
    }
}
