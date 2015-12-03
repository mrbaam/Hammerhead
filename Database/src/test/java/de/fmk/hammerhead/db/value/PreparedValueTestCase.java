package de.fmk.hammerhead.db.value;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class PreparedValueTestCase {
    @Test
    public void testGetType() throws Exception {
        final PreparedValue<String> val1 = new PreparedValue<String>(String.class);
        final PreparedValue<Double> val2 = new PreparedValue<Double>(Double.class);

        Assert.assertEquals(String.class, val1.getType());
        Assert.assertEquals(Double.class, val2.getType());
    }


    @Test
    public void testIndex() throws Exception {
        final PreparedValue<String> val;
        final IntegerProperty       prop;

        prop = new SimpleIntegerProperty();
        val  = new PreparedValue<String>(String.class);
        val.setIndex(0);

        prop.bind(val.indexProperty());

        Assert.assertEquals(0, val.getIndex());
        Assert.assertEquals(0, prop.get());

        val.setIndex(1);

        Assert.assertEquals(1, val.getIndex());
        Assert.assertEquals(1, prop.get());
    }


    @Test
    public void testValue() throws Exception {
        final PreparedValue<String>  val;
        final ObjectProperty<String> prop;

        prop = new SimpleObjectProperty<String>();
        val  = new PreparedValue<String>(String.class);
        val.setValue("Test");

        prop.bind(val.valueProperty());

        Assert.assertEquals("Test", val.getValue());
        Assert.assertEquals("Test", prop.get());

        val.setValue("new Test");

        Assert.assertEquals("new Test", val.getValue());
        Assert.assertEquals("new Test", prop.get());
    }
}
