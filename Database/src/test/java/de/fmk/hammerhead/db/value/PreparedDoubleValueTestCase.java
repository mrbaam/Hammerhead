package de.fmk.hammerhead.db.value;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class PreparedDoubleValueTestCase {
    @Test
    public void test() throws Exception {
        final PreparedDoubleValue val = new PreparedDoubleValue(0, 1.0);

        Assert.assertEquals(Double.class, val.getType());
        Assert.assertEquals(0, val.getIndex());
        Assert.assertEquals(1.0, val.getValue());
    }
}
