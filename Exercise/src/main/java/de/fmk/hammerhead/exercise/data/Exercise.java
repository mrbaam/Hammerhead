package de.fmk.hammerhead.exercise.data;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;
import java.util.Locale;

/**
 * Created by Fabian on 16.11.2015.
 */
public class Exercise {
    private final StringProperty                id;
    private final StringProperty                name;
    private final StringProperty                targetID;
    private final StringProperty                supportID;
    private final BooleanProperty               custom;
    private final ObjectProperty<MuscleGroup>   muscleGroup;
    private final ObservableList<Muscle>        targetMuscles;
    private final ObservableList<Muscle>        supportMuscles;
    private final ObservableMap<String, String> localizedNames;


    public Exercise(String guid) {
        id             = new SimpleStringProperty(guid);
        name           = new SimpleStringProperty();
        targetID       = new SimpleStringProperty();
        supportID      = new SimpleStringProperty();
        custom         = new SimpleBooleanProperty(false);
        muscleGroup    = new SimpleObjectProperty<>();
        targetMuscles  = FXCollections.observableArrayList();
        supportMuscles = FXCollections.observableArrayList();
        localizedNames = FXCollections.observableHashMap();
    }


    private String _getMusclesText(ObservableList<Muscle> muscles) {
        final StringBuilder builder = new StringBuilder();

        for (Muscle muscle : muscles) {
            builder.append(muscle.getName());

            if (!muscle.equals(muscles.get(muscles.size() - 1)))
                builder.append(", ");
        }

        return builder.toString();
    }


    public void addSupportMuscle(Muscle muscle) {
        supportMuscles.add(muscle);
    }

    public boolean containsSupportMuscle(String muscleID) {
        for (Muscle supportMuscle : supportMuscles) {
            if (supportMuscle.getID().equals(muscleID))
                return true;
        }

        return false;
    }

    public ObservableList<Muscle> getSupportMuscles() {
        return supportMuscles;
    }

    public String getSupportMusclesText() {
        if (supportMuscles.isEmpty())
            return "";

        return _getMusclesText(supportMuscles);
    }

    public String getSupportID() {
        return supportID.get();
    }

    public void setSupportMuscles(List<Muscle> muscles) {
        supportMuscles.clear();
        supportMuscles.addAll(muscles);
    }

    public void setSupportID(String id) {
        supportID.set(id);
    }


    public void addTargetMuscle(Muscle muscle) {
        targetMuscles.add(muscle);
    }

    public boolean containsTargetMuscle(String muscleID) {
        for (Muscle targetMuscle : targetMuscles) {
            if (targetMuscle.getID().equals(muscleID))
                return true;
        }

        return false;
    }

    public ObservableList<Muscle> getTargetMuscles() {
        return targetMuscles;
    }

    public String getTargetMusclesText() {
        if (targetMuscles.isEmpty())
            return "";

        return _getMusclesText(targetMuscles);
    }

    public String getTargetID() {
        return targetID.get();
    }

    public void setTargetMuscles(List<Muscle> muscles) {
        targetMuscles.clear();
        targetMuscles.addAll(muscles);
    }

    public void setTargetID(String id) {
        targetID.set(id);
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

    public String getName(String language) {
        return localizedNames.get(language.toLowerCase());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);

        localizedNames.put(Locale.getDefault().getLanguage().toLowerCase(), name);
    }

    public void setName(String language, String name) {
        localizedNames.put(language, name);
    }


    public boolean isCustom() {
        return custom.get();
    }

    public BooleanProperty customProperty() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom.set(custom);
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
