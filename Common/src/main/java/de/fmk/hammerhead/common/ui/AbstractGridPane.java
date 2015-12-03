package de.fmk.hammerhead.common.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Fabian on 13.11.2015.
 */
public abstract class AbstractGridPane extends GridPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGridPane.class);


    public AbstractGridPane(String fxml) {
        try {
            _initFXML(fxml);
        }
        catch (IOException ioex) {
            LOGGER.error("Could not load fxml from " + fxml + ".", ioex);
        }
    }


    private void _initFXML(String fxml) throws IOException {
        final ResourceBundle bundle;
        final FXMLLoader loader;

        bundle = ResourceBundle.getBundle("properties.resources", Locale.getDefault());
        loader = new FXMLLoader(getClass().getResource(fxml), bundle);

        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }


    public abstract void enter();
}
