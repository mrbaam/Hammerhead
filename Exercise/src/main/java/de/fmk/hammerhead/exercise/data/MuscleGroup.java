package de.fmk.hammerhead.exercise.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Fabian on 16.11.2015.
 */
public class MuscleGroup {
    private final StringProperty id;
    private final StringProperty name;


    public MuscleGroup(String guid) {
        id   = new SimpleStringProperty(guid);
        name = new SimpleStringProperty();
    }


    public String getID() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setID(String guid) {
        id.set(guid);
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
