package de.fmk.hammerhead.common.xml;

import de.fmk.hammerhead.common.xml.Column;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class ColumnTestCase {
    @Test
    public void test() {
        final Column col = new Column("test");

        Assert.assertEquals("test", col.getKey());

        col.setKey("key");
        col.setType("type");
        col.setValue("value");

        Assert.assertEquals("key", col.getKey());
        Assert.assertEquals("type", col.getType());
        Assert.assertEquals("value", col.getValue());
    }
}
