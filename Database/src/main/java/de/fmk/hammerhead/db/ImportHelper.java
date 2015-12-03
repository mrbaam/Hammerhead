package de.fmk.hammerhead.db;

import de.fmk.hammerhead.common.ImportException;
import de.fmk.hammerhead.common.xml.Column;
import de.fmk.hammerhead.common.xml.Entry;
import de.fmk.hammerhead.common.xml.XML_CONSTANTS;
import de.fmk.hammerhead.db.value.PreparedBooleanValue;
import de.fmk.hammerhead.db.value.PreparedStringValue;
import de.fmk.hammerhead.db.value.PreparedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 15.11.2015.
 */
public final class ImportHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportHelper.class);


    public void importData(String tableName, List<Entry> data, List<String> colNames) throws ImportException {
        final String query;

        query = QueryBuilder.createInsertOrReplaceQuery(tableName, colNames, QueryBuilder.INSERT);

        for (Entry entry : data) {
            final List<PreparedValue> preparedValues = new ArrayList<>();

            for (String colName : colNames) {
                final Column col;
                final String type;

                col  = entry.getColumn(colName);
                type = col.getType();

                switch (type) {
                    case XML_CONSTANTS.VA_GUID:
                    case XML_CONSTANTS.VA_LANG:
                        preparedValues.add(new PreparedStringValue(colNames.indexOf(colName) + 1, col.getValue()));
                        break;

                    case XML_CONSTANTS.VA_BOOLEAN:
                        preparedValues.add(new PreparedBooleanValue(colNames.indexOf(colName) + 1,
                                                                    Boolean.parseBoolean(col.getValue())));
                        break;

                    default:
                        throw new ImportException("'" + type + "' is an invalid type.");
                }
            }

            if (DBHandler.INSTANCE.insertIntoTable(query, preparedValues) > -1)
                LOGGER.info("Data was inserted into the table " + tableName + ".");
        }
    }
}
