package space.maxus.plib.utils;

import space.maxus.plib.PlatinumLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;

public class Utils {
    public static String readToEnd(InputStream stream) throws IOException {
        var build = new StringBuilder();
        var reader = new BufferedReader(new InputStreamReader(stream));
        int ch = reader.read();
        while(ch != -1) {
            build.append((char) ch);
            ch = reader.read();
        }

        return build.toString();
    }

    public static void logError(Exception e) {
        PlatinumLib.logger().log(Level.WARNING, e.getMessage());
        for(var ele:
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()) {
            PlatinumLib.logger().log(Level.WARNING, ele);
        }
    }

    public static Locale getLocaleFromString(String localeString)
    {
        if (localeString == null)
        {
            return null;
        }
        localeString = localeString.trim();
        if (localeString.equalsIgnoreCase("default"))
        {
            return Locale.getDefault();
        }

        int languageIndex = localeString.indexOf('_');
        String language;
        if (languageIndex == -1)
        {
            return new Locale(localeString, "");
        }
        else
        {
            language = localeString.substring(0, languageIndex);
        }

        int countryIndex = localeString.indexOf('_', languageIndex + 1);
        String country;
        if (countryIndex == -1)
        {
            country = localeString.substring(languageIndex+1);
            return new Locale(language, country);
        }
        else
        {
            country = localeString.substring(languageIndex+1, countryIndex);
            String variant = localeString.substring(countryIndex+1);
            return new Locale(language, country, variant);
        }
    }
}
