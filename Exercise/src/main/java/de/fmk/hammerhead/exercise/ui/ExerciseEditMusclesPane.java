package de.fmk.hammerhead.exercise.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.hammerhead.exercise.data.Exercise;
import de.fmk.hammerhead.exercise.data.Muscle;
import de.fmk.hammerhead.exercise.data.MuscleGroup;
import de.fmk.hammerhead.exercise.model.ExerciseEditPaneModel;
import de.fmk.hammerhead.exercise.ui.cells.MuscleListCell;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by Fabian on 21.11.2015.
 */
public class ExerciseEditMusclesPane extends AbstractDialogPane<Exercise> {
    private static final BooleanProperty NO_TARGET_MUSCLES = new SimpleBooleanProperty(true);

    @FXML
    private Button deselectSupportButton;
    @FXML
    private Button deselectTargetButton;
    @FXML
    private Button selectSupportButton;
    @FXML
    private Button selectTargetButton;
    @FXML
    private ChoiceBox<MuscleGroup> muscleGroupChoice;
    @FXML
    private ListView<Muscle> availableSupportMuscleList;
    @FXML
    private ListView<Muscle> availableTargetMuscleList;
    @FXML
    private ListView<Muscle> selectedSupportMuscleList;
    @FXML
    private ListView<Muscle> selectedTargetMuscleList;
    @FXML
    private TextField searchSupportField;
    @FXML
    private TextField searchTargetField;

    private ExerciseEditPaneModel model;

    private FilteredList<Muscle> filteredSupportMuscles;
    private FilteredList<Muscle> filteredTargetMuscles;


    public ExerciseEditMusclesPane(ExerciseEditPaneModel paneModel, String title, String subtitle) {
        super(ExerciseEditMusclesPane.class.getResource("/fxml/ExerciseEditMusclesPane.fxml"), paneModel);

        model = paneModel;

        setTitle(title);
        setSubtitle(subtitle);

        _initControls();
    }


    private void _initControls() {
        availableSupportMuscleList.setCellFactory(param -> new MuscleListCell());
        availableTargetMuscleList.setCellFactory(param -> new MuscleListCell());
        selectedSupportMuscleList.setCellFactory(param -> new MuscleListCell());
        selectedTargetMuscleList.setCellFactory(param -> new MuscleListCell());

        selectedTargetMuscleList.getItems().addListener((InvalidationListener) observable -> {
            NO_TARGET_MUSCLES.set(selectedTargetMuscleList.getItems().isEmpty());
        });

        searchSupportField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                filteredSupportMuscles.setPredicate(muscle -> _checkSelectedSupports(muscle));
            else
                _setSupportPredicate(newValue);
        });
        searchSupportField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                searchSupportField.setText("");
        });

        searchTargetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                filteredTargetMuscles.setPredicate(muscle -> _checkSelectedTargets(muscle));
            else
                _setTargetPredicate(newValue);
        });
        searchTargetField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                searchTargetField.setText("");
        });

        deselectSupportButton.disableProperty().bind(
                selectedSupportMuscleList.getSelectionModel().selectedItemProperty().isNull());
        deselectTargetButton.disableProperty().bind(
                selectedTargetMuscleList.getSelectionModel().selectedItemProperty().isNull());
        selectSupportButton.disableProperty().bind(
                availableSupportMuscleList.getSelectionModel().selectedItemProperty().isNull());
        selectTargetButton.disableProperty().bind(
                availableTargetMuscleList.getSelectionModel().selectedItemProperty().isNull());
    }


    private void _setSupportPredicate(String newValue) {
        filteredSupportMuscles.setPredicate(muscle -> {
            final String name = muscle.getName().toLowerCase();

            return _checkSelectedSupports(muscle) && name.contains(newValue.toLowerCase());
        });
    }


    private void _setTargetPredicate(String newValue) {
        filteredTargetMuscles.setPredicate(muscle -> {
            final String name = muscle.getName().toLowerCase();

            return _checkSelectedTargets(muscle) && name.contains(newValue.toLowerCase());
        });
    }


    private boolean _checkSelectedSupports(Muscle muscle) {
        for (Muscle m : selectedSupportMuscleList.getItems()) {
            if (m.getName().equals(muscle.getName()))
                return false;
        }

        return true;
    }


    private boolean _checkSelectedTargets(Muscle muscle) {
        for (Muscle m : selectedTargetMuscleList.getItems()) {
            if (m.getName().equals(muscle.getName()))
                return false;
        }

        return true;
    }


    protected @FXML void onDeselectSupport() {
        final Muscle muscle = selectedSupportMuscleList.getSelectionModel().getSelectedItem();

        selectedSupportMuscleList.getItems().remove(muscle);
        selectedSupportMuscleList.getItems().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        _setSupportPredicate(searchSupportField.getText());
    }


    protected @FXML void onDeselectTarget() {
        final Muscle muscle = selectedTargetMuscleList.getSelectionModel().getSelectedItem();

        selectedTargetMuscleList.getItems().remove(muscle);
        selectedTargetMuscleList.getItems().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        _setTargetPredicate(searchTargetField.getText());
    }


    protected @FXML void onSelectSupport() {
        final Muscle muscle = availableSupportMuscleList.getSelectionModel().getSelectedItem();

        if (muscle != null)
            selectedSupportMuscleList.getItems().add(muscle);

        selectedSupportMuscleList.getItems().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        _setSupportPredicate(searchSupportField.getText());
    }


    protected @FXML void onSelectTarget() {
        final Muscle muscle = availableTargetMuscleList.getSelectionModel().getSelectedItem();

        if (muscle != null)
            selectedTargetMuscleList.getItems().add(muscle);

        selectedTargetMuscleList.getItems().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        _setTargetPredicate(searchTargetField.getText());
    }


    @Override
    public void bindStorable() {
        storable.bind(muscleGroupChoice.getSelectionModel().selectedItemProperty().isNotNull()
                 .and(NO_TARGET_MUSCLES.not()));
    }


    @Override
    public void load() {
        final Exercise exercise = model.getObject();

        filteredSupportMuscles = new FilteredList<>(model.getMuscles(), muscle -> true);
        filteredTargetMuscles  = new FilteredList<>(model.getMuscles(), muscle -> true);

        muscleGroupChoice.setItems(model.getMuscleGroups());

        availableSupportMuscleList.setItems(filteredSupportMuscles);
        availableTargetMuscleList.setItems(filteredTargetMuscles);

        if (exercise.getMuscleGroup() != null) {
            for (MuscleGroup mGrp : muscleGroupChoice.getItems()) {
                if (mGrp.getID().equals(exercise.getMuscleGroup().getID())) {
                    muscleGroupChoice.getSelectionModel().select(mGrp);
                    break;
                }
            }
        }

        if (!exercise.getTargetMuscles().isEmpty()) {
            availableTargetMuscleList.getItems().stream()
                    .filter(muscle -> exercise.containsTargetMuscle(muscle.getID()))
                    .forEach(muscle -> selectedTargetMuscleList.getItems().add(muscle));

            onSelectTarget();
        }

        if (!exercise.getSupportMuscles().isEmpty()) {
            availableSupportMuscleList.getItems().stream()
                    .filter(muscle -> exercise.containsSupportMuscle(muscle.getID()))
                    .forEach(muscle -> selectedSupportMuscleList.getItems().add(muscle));

            onSelectSupport();
        }
    }


    @Override
    public void save() {
        model.getObject().setMuscleGroup(muscleGroupChoice.getSelectionModel().getSelectedItem());
        model.getObject().setTargetMuscles(selectedTargetMuscleList.getItems());
        model.getObject().setSupportMuscles(selectedSupportMuscleList.getItems());
    }
}
