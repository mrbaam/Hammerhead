package de.fmk.hammerhead.common.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fabian on 14.11.2015.
 */
public class Entry {
    private final Map<String, Column> columns;


    public Entry() {
        columns = new HashMap<>();
    }


    public void addColumn(Column col) {
        columns.put(col.getKey().toLowerCase(), col);
    }


    public Column getColumn(String key) {
        return columns.get(key.toLowerCase());
    }


    public Map<String, Column> getColumns() {
        return columns;
    }
}
