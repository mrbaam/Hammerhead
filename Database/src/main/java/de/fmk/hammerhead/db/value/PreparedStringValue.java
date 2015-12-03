package de.fmk.hammerhead.db.value;

/**
 * Created by FabianK on 13.11.2015.
 */
public class PreparedStringValue extends PreparedValue<String> {
    public PreparedStringValue() {
        super(String.class);
    }


    public PreparedStringValue(int index, String value) {
        super(String.class, index, value);
    }
}
