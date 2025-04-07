package dev.mirodil.utils;

import dev.mirodil.models.PhpVersion;


public class DataUtil {
    public static PhpVersion extractPhpVersionFromFileName(String fileName) {
        String versionPattern = "php_(\\d+)_(\\d+)-";
        return AgentUtil.extractPhpVersionFromString(fileName, versionPattern);
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
