package de.fmk.hammerhead.model.main;

import de.fmk.hammerhead.db.DBHandler;
import de.fmk.hammerhead.exercise.db.EXERCISE_DB_CONSTANTS;
import org.junit.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class MainModelTestCase {
    private static Path tmpDir;

    private MainModel model;


    @BeforeClass
    public static void beforeClass() throws Exception {
        tmpDir = Files.createTempDirectory("temp");
    }


    @Before
    public void setUp() throws Exception {
        model = new MainModel();
    }


    @Test
    public void testInitDatabase() throws Exception {
        model.initDatabase("jdbc:h2:" + tmpDir.toString() + "\\test", "", "");

        Assert.assertTrue(DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_EXERCISE_MUSCLE, false));
        Assert.assertTrue(DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_EXERCISES, false));
        Assert.assertTrue(DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_MUSCLES, false));
        Assert.assertTrue(DBHandler.INSTANCE.exists(EXERCISE_DB_CONSTANTS.TBL_MUSCLEGROUPS, false));
    }


    @AfterClass
    public static void afterClass() throws Exception {
        DBHandler.INSTANCE.closeConnection();

        Files.delete(Paths.get(tmpDir.toString(), "test.mv.db"));
        Files.delete(tmpDir);
    }
}
