package de.fmk.hammerhead.exercise.ui;


import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.factory.DialogFactory;
import de.fmk.dialogs.factory.WizardFactory;
import de.fmk.hammerhead.common.GUID;
import de.fmk.hammerhead.common.ui.AbstractGridPane;
import de.fmk.hammerhead.exercise.data.Exercise;
import de.fmk.hammerhead.exercise.data.MuscleGroup;
import de.fmk.hammerhead.exercise.model.ExerciseEditPaneModel;
import de.fmk.hammerhead.exercise.model.ExerciseOverviewPaneModel;
import de.fmk.hammerhead.exercise.ui.cells.AdvancedListCell;
import de.fmk.hammerhead.exercise.ui.cells.SimpleListCell;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 13.11.2015.
 */
public class ExerciseOverviewPane extends AbstractGridPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseOverviewPane.class);

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private ChoiceBox<MuscleGroup> muscleGroupChoice;
    @FXML
    private GridPane contentPane;
    @FXML
    private Label exerciseTitleLabel;
    @FXML
    private Label muscleGroupLabel;
    @FXML
    private Label supportMuscleLabel;
    @FXML
    private Label targetMuscleLabel;
    @FXML
    private ListView<Exercise> exercisesList;
    @FXML
    private RadioButton muscleGroupRadio;
    @FXML
    private TextField searchField;

    private boolean simpleView;

    private ExerciseOverviewPaneModel model;


    public ExerciseOverviewPane(ExerciseOverviewPaneModel paneModel) {
        super("/fxml/ExerciseOverviewPane.fxml");

        model = paneModel;

        _initControls();
    }


    private void _initControls() {
        onSimple();

        muscleGroupRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                model.changeExercises(searchField.getText(), null);
            else
                model.changeExercises(searchField.getText(), muscleGroupChoice.getSelectionModel().getSelectedItem());
        });

        muscleGroupChoice.disableProperty().bind(muscleGroupRadio.selectedProperty().not());
        muscleGroupChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (muscleGroupRadio.isSelected())
                model.changeExercises(searchField.getText(), newValue);
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (muscleGroupRadio.isSelected())
                model.changeExercises(newValue, muscleGroupChoice.getSelectionModel().getSelectedItem());
            else
                model.changeExercises(newValue, null);
        });

        exercisesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                _changeContent(newValue);

            editButton.setDisable(newValue == null || !newValue.isCustom());
            deleteButton.setDisable(newValue == null || !newValue.isCustom());
        });

        contentPane.visibleProperty().bind(exercisesList.getSelectionModel().selectedItemProperty().isNotNull());
    }


    private void _changeContent(Exercise exercise) {
        exerciseTitleLabel.setText(exercise.getName());
        muscleGroupLabel.setText(exercise.getMuscleGroup().getName());
        supportMuscleLabel.setText(exercise.getSupportMusclesText());
        targetMuscleLabel.setText(exercise.getTargetMusclesText());
    }


    protected @FXML void onAdd() {
        final List<AbstractDialogPane> panes;
        final ExerciseEditPaneModel    model;
        final ExerciseEditGeneralPane  generalPane;
        final ExerciseEditMusclesPane  musclePane;
        final Dimension2D              dimension;
        final Exercise                 exercise;

        exercise    = new Exercise(GUID.createInstance().toString());
        panes       = new ArrayList<>();
        model       = new ExerciseEditPaneModel(exercise, this.model.getMuscles(), this.model.getMuscleGroups());
        generalPane = new ExerciseEditGeneralPane(model, "Allgemein", "Füge allgemeine Informationen zur Übung hinzu");
        musclePane  = new ExerciseEditMusclesPane(model, "Trainierte Muskeln", "Weise der Übung die trainierten Muskeln zu");
        dimension   = new Dimension2D(400, 400);

        panes.add(generalPane);
        panes.add(musclePane);

        if (WizardFactory.createInstance(panes).createAndShowWizard(dimension, Modality.APPLICATION_MODAL)) {
            if (this.model.addExercise(model.getObject()))
                LOGGER.info("Exercise was created."); // TODO InfoBox!
            else
                LOGGER.warn("Exercise could not be created."); // TODO InfoBox!

            if (muscleGroupRadio.isSelected())
                this.model.changeExercises(searchField.getText(),
                        muscleGroupChoice.getSelectionModel().getSelectedItem());
            else
                this.model.changeExercises(searchField.getText(), null);

            exercisesList.getSelectionModel().select(exercise);
        }
    }


    protected @FXML void onDelete() {
        LOGGER.info("Would you really like to delete this exercise?"); // TODO QuestionBox!

        if (!model.deleteExercise(exercisesList.getSelectionModel().getSelectedItem()))
            LOGGER.error("Could not delete exercise."); // TODO ErrorBox!

        if (muscleGroupRadio.isSelected())
            this.model.changeExercises(searchField.getText(),
                    muscleGroupChoice.getSelectionModel().getSelectedItem());
        else
            this.model.changeExercises(searchField.getText(), null);

        exercisesList.getSelectionModel().clearAndSelect(0);
    }


    protected @FXML void onEdit() {
        final List<AbstractDialogPane> panes;
        final ExerciseEditPaneModel    model;
        final ExerciseEditGeneralPane  generalPane;
        final ExerciseEditMusclesPane  musclePane;
        final Dimension2D              dimension;
        final Exercise                 exercise;

        exercise    = exercisesList.getSelectionModel().getSelectedItem();
        panes       = new ArrayList<>();
        model       = new ExerciseEditPaneModel(exercise, this.model.getMuscles(), this.model.getMuscleGroups());
        generalPane = new ExerciseEditGeneralPane(model, "Allgemein", "Füge allgemeine Informationen zur Übung hinzu");
        musclePane  = new ExerciseEditMusclesPane(model, "Trainierte Muskeln", "Weise der Übung die trainierten Muskeln zu");
        dimension   = new Dimension2D(400, 400);

        panes.add(generalPane);
        panes.add(musclePane);

        if (DialogFactory.createInstance(panes).createAndShowDialog(dimension, Modality.APPLICATION_MODAL)) {
            if (this.model.editExercise(model.getObject()))
                LOGGER.info("Exercise was edited."); // TODO InfoBox!
            else
                LOGGER.warn("Exercise could not be edited."); // TODO InfoBox!

            if (muscleGroupRadio.isSelected())
                this.model.changeExercises(searchField.getText(),
                        muscleGroupChoice.getSelectionModel().getSelectedItem());
            else
                this.model.changeExercises(searchField.getText(), null);

            exercisesList.getSelectionModel().select(exercise);
        }
    }


    protected @FXML void onAdvanced() {
        if (simpleView) {
            exercisesList.setCellFactory(listView -> new AdvancedListCell());

            simpleView = false;
        }
    }


    protected @FXML void onSimple() {
        if (!simpleView) {
            exercisesList.setCellFactory(listView -> new SimpleListCell());
            simpleView = true;
        }
    }


    @Override
    public void enter() {
        try {
            model.loadExercises();
            exercisesList.setItems(model.getExercises());
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not load exercises.", sqlex);
        }

        try {
            model.loadMuscleGroups();
            muscleGroupChoice.setItems(model.getMuscleGroups());
            muscleGroupChoice.getSelectionModel().selectFirst();
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not load muscle groups.", sqlex);
        }

        try {
            model.loadMuscles();
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not load muscles.", sqlex);
        }
    }
}
