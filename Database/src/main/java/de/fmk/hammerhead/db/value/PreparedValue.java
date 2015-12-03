package de.fmk.hammerhead.db.value;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by FabianK on 13.11.2015.
 */
public class PreparedValue<T> {
    protected final Class<T> type;

    protected final IntegerProperty index;
    protected final ObjectProperty<T> value;


    public PreparedValue(Class<T> type) {
        this.type = type;

        index = new SimpleIntegerProperty();
        value = new SimpleObjectProperty<T>();
    }


    public PreparedValue(Class<T> type, int index, T value) {
        this.type  = type;
        this.index = new SimpleIntegerProperty(index);
        this.value = new SimpleObjectProperty<T>(value);
    }


    public int getIndex() {
        return index.get();
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }


    public Class<T> getType() {
        return type;
    }


    public T getValue() {
        return value.get();
    }

    public ObjectProperty<T> valueProperty() {
        return value;
    }

    public void setValue(T value) {
        this.value.set(value);
    }
}
