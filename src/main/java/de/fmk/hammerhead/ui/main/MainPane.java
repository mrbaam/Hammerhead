package de.fmk.hammerhead.ui.main;

import de.fmk.hammerhead.exercise.model.ExerciseOverviewPaneModel;
import de.fmk.hammerhead.model.main.MainModel;
import de.fmk.hammerhead.common.ui.AbstractGridPane;
import de.fmk.hammerhead.exercise.ui.ExerciseOverviewPane;
import de.fmk.hammerhead.training.model.PlanOverviewPaneModel;
import de.fmk.hammerhead.training.ui.PlanOverviewPane;
import javafx.fxml.FXML;

/**
 * Created by Fabian on 13.11.2015.
 */
public class MainPane extends AbstractGridPane {
    private MainModel model;

    private AbstractGridPane displayedPane;


    public MainPane(MainModel mainModel) {
        super("/fxml/MainPane.fxml");

        model = mainModel;
    }


    protected @FXML void onMyPlans() {
        final PlanOverviewPaneModel paneModel = new PlanOverviewPaneModel();
        final PlanOverviewPane      pane      = new PlanOverviewPane(paneModel);

        pane.enter();
        _changeDisplayedPane(pane);

        add(pane, 0, 1);

        displayedPane = pane;
    }


    protected @FXML void onExerciseOverview() {
        final ExerciseOverviewPaneModel paneModel = new ExerciseOverviewPaneModel();
        final ExerciseOverviewPane      pane      = new ExerciseOverviewPane(paneModel);

        pane.enter();
        _changeDisplayedPane(pane);

        add(pane, 0, 1);
    }


    @Override
    public void enter() {

    }


    private void _changeDisplayedPane(AbstractGridPane pane) {
        if (displayedPane != null)
            getChildren().remove(displayedPane);

        displayedPane = pane;
    }
}
