package space.maxus.plib.utils;

import space.maxus.plib.PlatinumLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
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
}
