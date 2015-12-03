package de.fmk.hammerhead.db.value;

import java.sql.Types;

/**
 * Created by Fabian on 30.11.2015.
 */
public class PreparedNullValue extends PreparedValue<Object> {
    private int sqlType;


    public PreparedNullValue(int type) {
        super(Object.class);

        this.sqlType = type;
    }


    public PreparedNullValue(int index, int type) {
        super(Object.class, index, null);

        if (Types.VARCHAR != type)
            throw new IllegalArgumentException("Illegal sql type.");

        this.sqlType = type;
    }


    public int getSqlType() {
        return sqlType;
    }
}
