package de.fmk.hammerhead.db.value;

/**
 * Created by FabianK on 13.11.2015.
 */
public class PreparedIntegerValue extends PreparedValue<Integer> {
    public PreparedIntegerValue() {
        super(Integer.class);
    }


    public PreparedIntegerValue(int index, Integer value) {
        super(Integer.class, index, value);
    }
}
