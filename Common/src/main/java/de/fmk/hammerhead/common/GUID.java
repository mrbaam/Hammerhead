package de.fmk.hammerhead.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Fabian on 21.11.2015.
 */
public final class GUID {
    private String uuid;


    private GUID(String... args) {
        _createUUID(args);
    }


    public static GUID createInstance(String... args) {
        return new GUID(args);
    }


    private void _createUUID(String... args) {
        final Date date;
        final DateFormat format;
        final Locale locale;
        final StringBuilder meshUp;

        date   = new Date();
        format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        locale = Locale.getDefault();
        meshUp = new StringBuilder();

        meshUp.append(format.format(date))
              .append(locale.getDisplayLanguage())
              .append(System.getProperty("user.name"))
              .append(System.getProperty("os.name"));

        for (int i = 0; i < args.length; i++)
            meshUp.append(args[i]);

        uuid = UUID.nameUUIDFromBytes(meshUp.toString().getBytes()).toString();
    }


    @Override
    public String toString() {
        return uuid;
    }
}
