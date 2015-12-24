package de.fmk.hammerhead.training.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.dialog.model.AbstractDialogPaneModel;
import de.fmk.hammerhead.training.model.PlanEditPaneModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Created by Fabian on 03.12.2015.
 */
public class PlanEditGeneralPane extends AbstractDialogPane<Object> {
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField nameField;


    public PlanEditGeneralPane(PlanEditPaneModel paneModel, String title, String subtitle) {
        super(PlanEditGeneralPane.class.getResource("/fxml/PlanEditGeneralPane.fxml"), paneModel);

        setTitle(title);
        setSubtitle(subtitle);
    }

    @Override
    public void bindStorable() {
        storable.bind(new SimpleBooleanProperty(true));
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
