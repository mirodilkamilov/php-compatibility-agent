package dev.mirodil.utils;

import dev.mirodil.models.PhpVersion;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentUtil {
    private static final String USER_MESSAGE_PATTERN = "^Check if this PHP code is compatible with PHP (\\d+).(\\d+)$";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    public static boolean isValidPhpFile(File file) {
        if (!isFileExists(file)) {
            return false;
        }

        String fileName = file.getName().toLowerCase();
        if (!fileName.endsWith(".php")) {
            return false;
        }

        return file.canRead();
    }

    public static boolean isValidUserMessage(String message) {
        if (message == null) return false;
        message = message.trim();

        return Pattern.matches(USER_MESSAGE_PATTERN, message);
    }

    public static PhpVersion extractPhpVersionFromUserMessage(String message) {
        return extractPhpVersionFromString(message, USER_MESSAGE_PATTERN);
    }

    public static PhpVersion extractPhpVersionFromString(String string, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            return PhpVersion.fromString(major + "." + minor).orElseThrow();
        }

        throw new IllegalArgumentException("Cannot extract php version from the given String: " + string);
    }
}
