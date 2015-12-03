package de.fmk.hammerhead.db;

import de.fmk.hammerhead.db.value.PreparedIntegerValue;
import de.fmk.hammerhead.db.value.PreparedStringValue;
import de.fmk.hammerhead.db.value.PreparedValue;
import org.junit.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class DBHandlerTestCase {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS TESTTABLE " +
                                         "(ID INT PRIMARY KEY NOT NULL, NAME VARCHAR(255));";
    private static final String INSERT = "INSERT INTO TESTTABLE VALUES(?,?);";
    private static final String READ   = "SELECT * FROM TESTTABLE WHERE ID=?;";
    private static final String TABLE  = "TESTTABLE";

    private static Path tmpDir;


    @BeforeClass
    public static void beforeClass() throws Exception {
        tmpDir = Files.createTempDirectory("temp");
    }


    @Before
    public void setUp() throws Exception {
        DBHandler.INSTANCE.initConnection("jdbc:h2:" + tmpDir.toString() + "\\h2Test", "", "");
    }


    @Test
    public void testInitConnection() throws Exception {
        Assert.assertFalse(DBHandler.INSTANCE.initConnection("", "", ""));
    }


    @Test
    public void testCloseConnection() throws Exception {
        Assert.assertTrue(DBHandler.INSTANCE.closeConnection());
    }


    @Test
    public void testCreateTable() throws Exception {
        Assert.assertTrue("Table has not been created.", DBHandler.INSTANCE.createTable(TABLE, CREATE));
        Assert.assertTrue("Table does not exist.", DBHandler.INSTANCE.exists(TABLE, false));
    }


    @Test
    public void testInsertIntoTable() throws Exception {
        final List<PreparedValue> values;
        final int                 result;

        values = new ArrayList<>();
        values.add(new PreparedIntegerValue(1, 1));
        values.add(new PreparedStringValue(2, "John"));

        DBHandler.INSTANCE.createTable(TABLE, CREATE);

        result = DBHandler.INSTANCE.insertIntoTable(INSERT, values);

        Assert.assertEquals(1, result);
    }


    @Test
    public void testReadTable() throws Exception {
        final List<PreparedValue> insertValues;
        final List<PreparedValue> readValues;
        final ResultSet           result;

        insertValues = new ArrayList<>();
        readValues   = new ArrayList<>();

        insertValues.add(new PreparedIntegerValue(1, 2));
        insertValues.add(new PreparedStringValue(2, "Jane"));

        readValues.add(new PreparedIntegerValue(1, 2));

        DBHandler.INSTANCE.createTable(TABLE, CREATE);
        DBHandler.INSTANCE.insertIntoTable(INSERT, insertValues);

        result = DBHandler.INSTANCE.readTable(READ, readValues);

        Assert.assertTrue(result.next());
        Assert.assertEquals(2, result.getInt(1));
        Assert.assertEquals("Jane", result.getString(2));

        DBHandler.INSTANCE.closeStatement();
    }


    @Test
    public void testExists() throws Exception {
        Assert.assertTrue(DBHandler.INSTANCE.exists("USERS", true));
        Assert.assertFalse(DBHandler.INSTANCE.exists("Test", true));
    }


    @Test
    public void testGetColumnsOfTable() throws Exception {
        final List<String> columns = DBHandler.INSTANCE.getColumnsOfTable("USERS");

        Assert.assertEquals(4, columns.size());

        for (String name : columns) {
            Assert.assertTrue("NAME".equals(name) || "ADMIN".equals(name) ||
                              "REMARKS".equals(name) || "ID".equals(name));
        }
    }


    @Test
    public void testTableHasData() throws Exception {
        final List<PreparedValue> values = new ArrayList<>();

        DBHandler.INSTANCE.createTable("TEST2", "CREATE TABLE IF NOT EXISTS TEST2 (ID INT PRIMARY KEY);");

        Assert.assertFalse(DBHandler.INSTANCE.tableHasData("TEST2"));

        values.add(new PreparedIntegerValue(1, 10));

        DBHandler.INSTANCE.insertIntoTable("INSERT INTO TEST2 VALUES(?);", values);

        Assert.assertTrue(DBHandler.INSTANCE.tableHasData("TEST2"));
    }


    @After
    public void tearDown() throws Exception {
        DBHandler.INSTANCE.closeConnection();
    }


    @AfterClass
    public static void afterClass() throws Exception {
        Files.delete(Paths.get(tmpDir.toString(), "h2Test.mv.db"));
        Files.delete(tmpDir);
    }
}
