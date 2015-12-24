package de.fmk.hammerhead.training.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.factory.WizardFactory;
import de.fmk.hammerhead.common.ui.AbstractGridPane;
import de.fmk.hammerhead.training.model.PlanEditPaneModel;
import de.fmk.hammerhead.training.model.PlanOverviewPaneModel;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 30.11.2015.
 */
public class PlanOverviewPane extends AbstractGridPane {
    final PlanOverviewPaneModel model;


    public PlanOverviewPane(PlanOverviewPaneModel paneModel) {
        super("/fxml/PlanOverviewPane.fxml");

        model = paneModel;
    }


    protected @FXML void onAdd() {
        final List<AbstractDialogPane> panes;
        final PlanEditPaneModel model;
        final PlanEditGeneralPane generalPane;
        final PlanEditDaysOverviewPane overviewPane;
        final Dimension2D dimension;

        panes       = new ArrayList<>();
        model       = new PlanEditPaneModel(new Object());
        generalPane = new PlanEditGeneralPane(model, "Allgemein", "Füge allgemeine Informationen zum Trainingsplan hinzu");
        overviewPane = new PlanEditDaysOverviewPane(model, "Trainingstage", "Definiere deine Trainingstage");
        dimension   = new Dimension2D(400, 400);

        panes.add(generalPane);
        panes.add(overviewPane);

        if (WizardFactory.createInstance(panes).createAndShowWizard(dimension, Modality.APPLICATION_MODAL)) {

        }
    }


    @Override
    public void enter() {

    }
}
