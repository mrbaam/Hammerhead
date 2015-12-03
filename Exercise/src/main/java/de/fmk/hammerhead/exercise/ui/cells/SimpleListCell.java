package de.fmk.hammerhead.exercise.ui.cells;

import de.fmk.hammerhead.exercise.data.Exercise;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 * Created by Fabian on 16.11.2015.
 */
public class SimpleListCell extends ListCell<Exercise> {
    @Override
    protected void updateItem(Exercise item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty)
            setText(item.getName());
        else
            setText(null);
    }
}
