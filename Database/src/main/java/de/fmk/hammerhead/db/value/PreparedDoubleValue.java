package de.fmk.hammerhead.db.value;

/**
 * Created by FabianK on 13.11.2015.
 */
public class PreparedDoubleValue extends PreparedValue<Double> {
    public PreparedDoubleValue() {
        super(Double.class);
    }


    public PreparedDoubleValue(int index, Double value) {
        super(Double.class, index, value);
    }
}
