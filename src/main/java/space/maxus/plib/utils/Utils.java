package space.maxus.plib.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.maxus.plib.PlatinumLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {
    public static @NotNull String readToEnd(@NotNull InputStream stream) throws IOException {
        var build = new StringBuilder();
        var reader = new BufferedReader(new InputStreamReader(stream));
        int ch = reader.read();
        while (ch != -1) {
            build.append((char) ch);
            ch = reader.read();
        }

        return build.toString();
    }

    public static void logError(@NotNull Exception e) {
        PlatinumLib.logger().log(Level.WARNING, e.getMessage());
        for (var ele :
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()) {
            PlatinumLib.logger().log(Level.WARNING, ele);
        }
    }

    public static @Nullable Locale getLocaleFromString(@Nullable String localeString) {
        if (localeString == null) {
            return null;
        }
        localeString = localeString.trim();
        if (localeString.equalsIgnoreCase("default")) {
            return Locale.getDefault();
        }

        int languageIndex = localeString.indexOf('_');
        String language;
        if (languageIndex == -1) {
            return new Locale(localeString, "");
        } else {
            language = localeString.substring(0, languageIndex);
        }

        int countryIndex = localeString.indexOf('_', languageIndex + 1);
        String country;
        if (countryIndex == -1) {
            country = localeString.substring(languageIndex + 1);
            return new Locale(language, country);
        } else {
            country = localeString.substring(languageIndex + 1, countryIndex);
            String variant = localeString.substring(countryIndex + 1);
            return new Locale(language, country, variant);
        }
    }

    public static void zipDir(Path sourceDirPath, Path zipFilePath) throws IOException {
        Files.deleteIfExists(zipFilePath);
        var p = Files.createFile(zipFilePath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Files.walk(sourceDirPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            logError(e);
                        }
                    });
        }
    }
}
