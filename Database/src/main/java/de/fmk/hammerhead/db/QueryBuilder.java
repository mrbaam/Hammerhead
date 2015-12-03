package de.fmk.hammerhead.db;

import java.util.List;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class QueryBuilder {
    public static final String INSERT  = "INSERT INTO ";
    public static final String REPLACE = "REPLACE INTO ";


    public static String createInsertOrReplaceQuery(String tableName, List<String> columnNames, String type) {
        final StringBuilder builder;
        final StringBuilder valuesBuilder;

        if (!INSERT.equals(type) && !REPLACE.equals(type))
            throw new IllegalArgumentException("'" + type + "' is not a valid query type.");

        if (tableName == null || tableName.isEmpty())
            throw new IllegalArgumentException("The name of the table must not be null.");

        if (columnNames == null || columnNames.isEmpty())
            throw new IllegalArgumentException("There must be at least one column available.");

        builder       = new StringBuilder(type);
        valuesBuilder = new StringBuilder(" VALUES (");

        builder.append(tableName.toUpperCase());
        builder.append(" (");

        for (int i = 1; i <= columnNames.size(); i++) {
            builder.append(columnNames.get(i - 1).toUpperCase());
            valuesBuilder.append("?");

            if (i < columnNames.size()) {
                builder.append(", ");
                valuesBuilder.append(", ");
            }
        }

        builder.append(")");
        valuesBuilder.append(");");

        return builder.toString() + valuesBuilder.toString();
    }


    public static String createDeleteQuery(String tableName, List<String> columnNames) {
        final StringBuilder builder;

        if (tableName == null || tableName.isEmpty())
            throw new IllegalArgumentException("The name of the table must not be null.");

        if (columnNames == null || columnNames.isEmpty())
            throw new IllegalArgumentException("There must be at least one column available.");

        builder = new StringBuilder("DELETE FROM ");
        builder.append(tableName).append(" WHERE ");

        for (int i = 1; i <= columnNames.size(); i++) {
            builder.append(columnNames.get(i - 1).toUpperCase()).append("=?");

            if (i < columnNames.size())
                builder.append(" AND ");
        }

        builder.append(";");

        return builder.toString();
    }
}
