package de.fmk.hammerhead.exercise.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.dialog.model.AbstractDialogPaneModel;
import de.fmk.hammerhead.exercise.data.Exercise;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;

/**
 * Created by Fabian on 21.11.2015.
 */
public class ExerciseEditGeneralPane extends AbstractDialogPane<Exercise> {
    @FXML
    private TextField nameField;


    public ExerciseEditGeneralPane(AbstractDialogPaneModel<Exercise> paneModel, String title, String subtitle) {
        super(ExerciseEditGeneralPane.class.getResource("/fxml/ExerciseEditGeneralPane.fxml"), paneModel);

        setTitle(title);
        setSubtitle(subtitle);
    }


    @Override
    public void bindStorable() {
        storable.bind(nameField.textProperty().isNotNull());
    }


    @Override
    public void load() {
        nameField.setText(model.getObject().getName());
    }


    @Override
    public void save() {
        model.getObject().setName(nameField.getText());
    }
}
