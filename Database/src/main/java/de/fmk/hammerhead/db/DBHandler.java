package de.fmk.hammerhead.db;

import de.fmk.hammerhead.db.value.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianK on 13.11.2015.
 */
public final class DBHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBHandler.class);

    public static final DBHandler INSTANCE = new DBHandler();

    private Connection        connection;
    private PreparedStatement readStmt;


    private DBHandler() {}


    public boolean initConnection(String url, String name, String password) throws SQLException {
        if (connection != null && !connection.isClosed())
            return false;

        LOGGER.info("Connecting to database at " + url + "...");

        connection = DriverManager.getConnection(url, name, password);

        LOGGER.info("Connection established.");

        return true;
    }


    public boolean closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            LOGGER.info("Closing connection to database...");

            connection.close();
            connection = null;

            LOGGER.info("Connection closed.");

            return true;
        }
        else if (connection != null && connection.isClosed()) {
            connection = null;

            return true;
        }
        else if (connection == null) {
            return true;
        }

        return false;
    }


    public boolean createTable(String tableName, String query) {
        PreparedStatement pStmt = null;

        try {
            LOGGER.info("Creating table " + tableName.toUpperCase() + "...");

            pStmt = connection.prepareStatement(query);
            pStmt.executeUpdate();

            return exists(tableName, false);
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not create the table " + tableName.toUpperCase() + ".", sqlex);
        }
        finally {
            _close(pStmt);
        }

        return false;
    }


    public int insertIntoTable(String query, List<PreparedValue> values) {
        PreparedStatement pStmt  = null;
        int               result = -1;

        if (values == null || values.isEmpty())
            throw new IllegalArgumentException("No values available to insert into database.");

        try {
            LOGGER.info("Inserting data into database...");

            pStmt = connection.prepareStatement(query);

            _prepareStatement(pStmt, values);

            result = pStmt.executeUpdate();

            return result;
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not insert data into database.", sqlex);
        }
        finally {
            _close(pStmt);
        }

        return result;
    }


    public ResultSet readTable(String query, List<PreparedValue> values) {
        ResultSet result = null;

        try {
            LOGGER.info("Reading data from database...");

            readStmt = connection.prepareStatement(query);

            if (values != null && !values.isEmpty())
                _prepareStatement(readStmt, values);

            result = readStmt.executeQuery();

            LOGGER.info("Data was read.");

            return result;
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not read data from database.", sqlex);
        }

        return result;
    }


    public int deleteFromTable(String query, List<PreparedValue> values) {
        PreparedStatement pStmt  = null;
        int               result = -1;

        try {
            LOGGER.info("Deleting from database...");
            pStmt = connection.prepareStatement(query);

            _prepareStatement(pStmt, values);

            result = pStmt.executeUpdate();

            LOGGER.info("Data was deleted.");

            return result;
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not delete data from table.", sqlex);
        }
        finally {
            _close(pStmt);
        }

        return result;
    }


    public boolean exists(String tableName, boolean systemTables) {
        final DatabaseMetaData metaData;
        final String           lcTableName;
        final String[]         types;
        final ResultSet        existingTables;

        if (tableName == null || tableName.isEmpty())
            throw new IllegalArgumentException("The name of the table must not be null!");

        lcTableName = tableName.toLowerCase();

        if (systemTables)
            types = new String[] { "TABLE", "SYSTEM TABLE" };
        else
            types = new String[] { "TABLE" };

        try {
            metaData       = connection.getMetaData();
            existingTables = metaData.getTables(null, null, "%", types);

            while (existingTables.next()) {
                if (lcTableName.equals(existingTables.getString(3).toLowerCase()))
                    return true;
            }
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not get meta data from database.", sqlex);
        }

        return false;
    }


    public List<String> getColumnsOfTable(String tableName) {
        final DatabaseMetaData metaData;
        final ResultSet        columns;
        final List<String>     colNames;

        colNames = new ArrayList<>();

        try {
            metaData = connection.getMetaData();
            columns  = metaData.getColumns(null, null, tableName, null);

            while (columns.next())
                colNames.add(columns.getString("COLUMN_NAME"));
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not get meta data from database.", sqlex);
        }

        return colNames;
    }


    public boolean tableHasData(String tableName) {
        final Statement stmt;
        final ResultSet result;

        try {
            stmt   = connection.createStatement();
            result = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName.toUpperCase() + ";");

            if (result.next()) {
                return result.getInt(1) > 0;
            }

        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not check table " + tableName + ".", sqlex);
        }

        return false;
    }


    public void closeStatement() {
        _close(readStmt);
    }


    private void _close(PreparedStatement pStmt) {
        try {
            if (pStmt != null && !pStmt.isClosed())
                pStmt.close();
        }
        catch (SQLException sqlex) {
            LOGGER.error("Could not close statement.", sqlex);
        }
    }


    private void _prepareStatement(PreparedStatement pStmt, List<PreparedValue> values) throws SQLException {
        for (PreparedValue value : values) {
            if (value.getType().equals(Boolean.class))
                pStmt.setBoolean(value.getIndex(), ((PreparedBooleanValue) value).getValue());
            else if (value.getType().equals(Double.class))
                pStmt.setDouble(value.getIndex(), ((PreparedDoubleValue) value).getValue());
            else if (value.getType().equals(Integer.class))
                pStmt.setInt(value.getIndex(), ((PreparedIntegerValue) value).getValue());
            else if (value.getType().equals(String.class))
                pStmt.setString(value.getIndex(), ((PreparedStringValue) value).getValue());
            else if (value instanceof PreparedNullValue)
                pStmt.setNull(value.getIndex(), ((PreparedNullValue) value).getSqlType());
        }
    }
}
