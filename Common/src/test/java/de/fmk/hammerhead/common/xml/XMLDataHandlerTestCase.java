package de.fmk.hammerhead.common.xml;

import de.fmk.hammerhead.common.xml.Column;
import de.fmk.hammerhead.common.xml.Entry;
import de.fmk.hammerhead.common.xml.XMLDataHandler;
import de.fmk.hammerhead.common.xml.XML_CONSTANTS;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class XMLDataHandlerTestCase {
    @Test
    public void test() throws Exception {
        final Path           testFile;
        final XMLDataHandler handler;
        final List<Entry>    entries;

        Entry entry;

        testFile = Paths.get(getClass().getResource("/xml/testXMLDataHandler.xml").toURI());
        handler  = new XMLDataHandler(testFile);
        entries  = handler.parse();

        Assert.assertFalse(entries.isEmpty());
        Assert.assertEquals(2, entries.size());

        entry = entries.get(0);

        for (Column col : entry.getColumns().values()) {
            if ("id".equals(col.getKey())) {
                Assert.assertEquals(XML_CONSTANTS.VA_GUID, col.getType());
                Assert.assertEquals("23630eb4-1909-3258-9927-41c49f643991", col.getValue());
            }
            else if ("de".equals(col.getKey())) {
                Assert.assertEquals(XML_CONSTANTS.VA_LANG, col.getType());
                Assert.assertEquals("Bauch", col.getValue());
            }
            else if ("en".equals(col.getKey())) {
                Assert.assertEquals(XML_CONSTANTS.VA_LANG, col.getType());
                Assert.assertEquals("Abdomen", col.getValue());
            }
        }
    }
}
