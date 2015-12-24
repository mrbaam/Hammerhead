package de.fmk.hammerhead.training.ui;

import de.fmk.dialogs.dialog.AbstractDialogPane;
import de.fmk.dialogs.dialog.model.AbstractDialogPaneModel;
import de.fmk.hammerhead.training.model.PlanEditPaneModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by FabianK on 24.12.2015.
 */
public class PlanEditDaysOverviewPane extends AbstractDialogPane<Object> {
    @FXML
    private Spinner<Integer> daySpinner;
    @FXML
    private TabPane dayTabPane;

    private final ObservableMap<Integer, Tab> tabMap;
    private final PlanEditPaneModel           model;


    public PlanEditDaysOverviewPane(PlanEditPaneModel paneModel, String title, String subtitle) {
        super(PlanEditDaysOverviewPane.class.getResource("/fxml/PlanEditDaysOverviewPane.fxml"), paneModel);

        model  = paneModel;
        tabMap = FXCollections.observableHashMap();

        setTitle(title);
        setSubtitle(subtitle);

        _init();
    }


    private void _init() {
        _initTabs();

        daySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 7, 1, 1));
        daySpinner.valueProperty().addListener((observable, oldValue, newValue) -> _handleTabs(newValue, oldValue));
    }


    private void _initTabs() {
        for (int i = 1; i <= 7; i++) {
            final Tab tab = new Tab(Integer.toString(i));

            tab.setContent(new PlanEditDayPane(model));

            tabMap.put(Integer.valueOf(i), tab);
        }
    }


    private void _handleTabs(Integer newValue, Integer oldValue) {
        final int index = dayTabPane.getSelectionModel().getSelectedIndex();

        if (dayTabPane.getTabs().size() < newValue) {
            dayTabPane.getTabs().add(tabMap.get(newValue));
        }
        else if (dayTabPane.getTabs().size() > newValue) {
            dayTabPane.getTabs().remove(tabMap.get(oldValue));

            if (index >= dayTabPane.getTabs().size())
                dayTabPane.getSelectionModel().clearAndSelect(dayTabPane.getTabs().size() - 1);
        }
    }


    @Override
    public void bindStorable() {

    }


    @Override
    public void load() {
        dayTabPane.getTabs().add(tabMap.get(1));
    }


    @Override
    public void save() {

    }
}
