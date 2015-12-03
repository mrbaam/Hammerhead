package de.fmk.hammerhead.common.xml;

import de.fmk.hammerhead.common.xml.Column;
import de.fmk.hammerhead.common.xml.Entry;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class EntryTestCase {
    @Test
    public void test() {
        final Entry entry = new Entry();

        Assert.assertTrue(entry.getColumns().isEmpty());

        entry.addColumn(new Column("Test"));

        Assert.assertFalse(entry.getColumns().isEmpty());
        Assert.assertNotNull(entry.getColumn("Test"));
    }
}
