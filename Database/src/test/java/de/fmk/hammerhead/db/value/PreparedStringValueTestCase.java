package de.fmk.hammerhead.db.value;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class PreparedStringValueTestCase {
    @Test
    public void test() throws Exception {
        final PreparedStringValue val = new PreparedStringValue(0, "Test");

        Assert.assertEquals(String.class, val.getType());
        Assert.assertEquals(0, val.getIndex());
        Assert.assertEquals("Test", val.getValue());
    }
}
