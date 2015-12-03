package de.fmk.hammerhead.db;

import de.fmk.hammerhead.common.xml.Column;
import de.fmk.hammerhead.common.xml.Entry;
import de.fmk.hammerhead.common.xml.XML_CONSTANTS;
import org.junit.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 15.11.2015.
 */
public final class ImportHelperTestCase {
    private static Path tmpDir;

    private ImportHelper helper;


    @BeforeClass
    public static void beforeClass() throws Exception {
        tmpDir = Files.createTempDirectory("temp");
    }


    @Before
    public void setUp() throws Exception {
        helper = new ImportHelper();

        DBHandler.INSTANCE.initConnection("jdbc:h2:" + tmpDir.toString() + "\\testImportHelper", "", "");
        DBHandler.INSTANCE.createTable("TEST", "CREATE TABLE IF NOT EXISTS TEST " +
                                               "(ID VARCHAR(36) PRIMARY KEY NOT NULL, DE VARCHAR(255));");
    }


    @Test
    public void testImportMusclegroupData() throws Exception {
        final List<Entry>  data;
        final List<String> colNames;
        final ResultSet    result;

        Entry entry;

        data     = new ArrayList<>();
        colNames = new ArrayList<>();

        entry = new Entry();
        entry.addColumn(new Column("ID", XML_CONSTANTS.VA_GUID, "525f1ac8-1e4c-389d-857e-3e2d5c73cd15"));
        entry.addColumn(new Column("DE", XML_CONSTANTS.VA_LANG, "deutsch"));
        data.add(entry);

        entry = new Entry();
        entry.addColumn(new Column("ID", XML_CONSTANTS.VA_GUID, "afc99b8f-096d-3e94-a871-5c38e3813a0d"));
        entry.addColumn(new Column("DE", XML_CONSTANTS.VA_LANG, "englisch"));
        data.add(entry);

        colNames.add("ID");
        colNames.add("DE");

        helper.importData("TEST", data, colNames);

        result = DBHandler.INSTANCE.readTable("SELECT * FROM TEST", null);

        Assert.assertTrue(result.next());
        Assert.assertEquals("525f1ac8-1e4c-389d-857e-3e2d5c73cd15", result.getString(1));
        Assert.assertEquals("deutsch", result.getString(2));

        Assert.assertTrue(result.next());
        Assert.assertEquals("afc99b8f-096d-3e94-a871-5c38e3813a0d", result.getString(1));
        Assert.assertEquals("englisch", result.getString(2));

        DBHandler.INSTANCE.closeStatement();
    }


    @After
    public void tearDown() throws Exception {
        helper = null;

        DBHandler.INSTANCE.closeConnection();
    }


    @AfterClass
    public static void afterClass() throws Exception {
        Files.delete(Paths.get(tmpDir.toString(), "testImportHelper.mv.db"));
        Files.delete(tmpDir);
    }
}
