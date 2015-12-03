package de.fmk.hammerhead.exercise.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Fabian on 16.11.2015.
 */
public class Muscle {
    private final StringProperty              id;
    private final StringProperty              name;
    private final ObjectProperty<MuscleGroup> muscleGroup;


    public Muscle(String guid) {
        id          = new SimpleStringProperty(guid);
        name        = new SimpleStringProperty();
        muscleGroup = new SimpleObjectProperty<>();
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


    public MuscleGroup getMuscleGroup() {
        return muscleGroup.get();
    }

    public ObjectProperty<MuscleGroup> muscleGroupProperty() {
        return muscleGroup;
    }

    public void setMuscleGroup(MuscleGroup group) {
        muscleGroup.set(group);
    }
}
