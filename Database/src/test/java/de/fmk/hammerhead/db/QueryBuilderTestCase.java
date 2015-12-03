package de.fmk.hammerhead.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class QueryBuilderTestCase {
    @Test
    public void testCreateInsertQuery() throws Exception {
        final List<String> columnNames = new ArrayList<>();

        columnNames.add("ID");
        columnNames.add("NAME");

        Assert.assertEquals("INSERT INTO TEST (ID, NAME) VALUES (?, ?);",
                            QueryBuilder.createInsertOrReplaceQuery("TEST", columnNames, QueryBuilder.INSERT));

        Assert.assertEquals("REPLACE INTO TEST (ID, NAME) VALUES (?, ?);",
                            QueryBuilder.createInsertOrReplaceQuery("TEST", columnNames, QueryBuilder.REPLACE));
    }
}
