package de.fmk.hammerhead.db.value;

/**
 * Created by Fabian on 19.11.2015.
 */
public class PreparedBooleanValue extends PreparedValue<Boolean> {
    public PreparedBooleanValue() {
        super(Boolean.class);
    }


    public PreparedBooleanValue(int index, Boolean value) {
        super(Boolean.class, index, value);
    }
}
