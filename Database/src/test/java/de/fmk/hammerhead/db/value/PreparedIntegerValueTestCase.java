package de.fmk.hammerhead.db.value;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class PreparedIntegerValueTestCase {
    @Test
    public void test() throws Exception {
        final PreparedIntegerValue val = new PreparedIntegerValue(0, 1);

        Assert.assertEquals(Integer.class, val.getType());
        Assert.assertEquals(0, val.getIndex());
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(val.getValue()));
    }
}
