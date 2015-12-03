package de.fmk.hammerhead.db.value;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class PreparedBooleanValueTestCase {
    @Test
    public void test() throws Exception {
        final PreparedBooleanValue val = new PreparedBooleanValue(0, false);

        Assert.assertEquals(Boolean.class, val.getType());
        Assert.assertEquals(0, val.getIndex());
        Assert.assertEquals(Boolean.FALSE, val.getValue());
    }
}
