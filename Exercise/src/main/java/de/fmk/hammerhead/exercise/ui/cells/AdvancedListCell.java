package de.fmk.hammerhead.exercise.ui.cells;

import de.fmk.hammerhead.exercise.data.Exercise;
import de.fmk.hammerhead.exercise.data.Muscle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by Fabian on 16.11.2015.
 */
public class AdvancedListCell extends ListCell<Exercise> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedListCell.class);

    @FXML
    private Label muscleGroupLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label targetMusclesLabel;


    @Override
    protected void updateItem(Exercise item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty)
            setGraphic(_createGridPane(item));
        else
            setGraphic(null);
    }


    private GridPane _createGridPane(Exercise exercise) {
        final GridPane pane;
        final Label    muscleGroupLabel;
        final Label    nameLabel;
        final Label    targetMusclesLabel;

        ColumnConstraints col;
        RowConstraints    row;

        pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(2);

        col = new ColumnConstraints(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        col.setHgrow(Priority.NEVER);
        pane.getColumnConstraints().add(col);

        col = new ColumnConstraints(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        col.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().add(col);

        row = new RowConstraints(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        row.setVgrow(Priority.NEVER);
        pane.getRowConstraints().addAll(row, row);

        muscleGroupLabel   = new Label(exercise.getMuscleGroup().getName());
        nameLabel          = new Label(exercise.getName());
        targetMusclesLabel = new Label(_getTargetMuscles(exercise));

        pane.add(nameLabel, 0, 0, 2, 1);
        pane.add(muscleGroupLabel, 0, 1);
        pane.add(targetMusclesLabel, 1, 1);

        return pane;
    }


    private String _getTargetMuscles(Exercise exercise) {
        final StringBuilder builder = new StringBuilder();

        for (Muscle muscle : exercise.getTargetMuscles()) {
            builder.append(muscle.getName());

            if (!muscle.equals(exercise.getTargetMuscles().get(exercise.getTargetMuscles().size() - 1)))
                builder.append(", ");
        }

        return builder.toString();
    }
}
