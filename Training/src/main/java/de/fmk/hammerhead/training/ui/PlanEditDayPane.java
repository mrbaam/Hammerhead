package de.fmk.hammerhead.training.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.hammerhead.common.ui.AbstractGridPane;
import de.fmk.hammerhead.training.model.PlanEditPaneModel;
import javafx.scene.Node;

/**
 * Created by FabianK on 24.12.2015.
 */
public class PlanEditDayPane extends AbstractDialogPane<Object> {
    public PlanEditDayPane(PlanEditPaneModel paneModel) {
        super(PlanEditDayPane.class.getResource("/fxml/PlanEditPayPane.fxml"), paneModel);
    }

    @Override
    public void bindStorable() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
