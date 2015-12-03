package de.fmk.hammerhead.exercise.ui.cells;

import de.fmk.hammerhead.exercise.data.Muscle;
import javafx.scene.control.ListCell;

/**
 * Created by Fabian on 16.11.2015.
 */
public class MuscleListCell extends ListCell<Muscle> {
    @Override
    protected void updateItem(Muscle item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty)
            setText(item.getName());
        else
            setText(null);
    }
}
