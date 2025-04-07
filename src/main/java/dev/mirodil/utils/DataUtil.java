package dev.mirodil.utils;

import dev.mirodil.models.PhpVersion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {
    public static PhpVersion extractPhpVersionFromFileName(String fileName) {
        String versionPattern = "php_(\\d+)_(\\d+)-"; // Regex to capture version numbers

        Pattern pattern = Pattern.compile(versionPattern);
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            return PhpVersion.fromString(major + "." + minor).orElseThrow();
        }

        throw new IllegalArgumentException("Cannot extract php version from file name: " + fileName);
    }

    public static String toPascalCase(String snakeCase) {
        String[] parts = snakeCase.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }
        return sb.toString();
    }
}
