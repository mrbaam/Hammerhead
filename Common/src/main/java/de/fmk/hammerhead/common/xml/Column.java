package de.fmk.hammerhead.common.xml;

/**
 * Created by Fabian on 14.11.2015.
 */
public class Column {
    private String key;
    private String type;
    private String value;


    public Column(String key) {
        this.key = key;
    }


    public Column(String key, String type, String value) {
        this(key);

        setType(type);
        setValue(value);
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
