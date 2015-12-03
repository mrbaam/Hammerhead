package de.fmk.hammerhead.main;

import de.fmk.hammerhead.db.DBHandler;
import de.fmk.hammerhead.model.main.MainModel;
import de.fmk.hammerhead.ui.main.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by FabianK on 13.11.2015.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void init() throws Exception {
        super.init();

        Class.forName("org.h2.Driver");
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        final MainModel model;
        final MainPane  root;
        final Scene     scene;

        model  = new MainModel();
        root   = new MainPane(model);
        scene  = new Scene(root, 800, 600);

        model.initDatabase("jdbc:h2:~/.hammerhead/data", "Fabian", "test");

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        DBHandler.INSTANCE.closeConnection();

        super.stop();
    }
}
