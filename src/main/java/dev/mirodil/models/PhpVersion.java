package dev.mirodil.models;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum PhpVersion {
    PHP_8_0("8.0"),
    PHP_8_1("8.1"),
    PHP_8_2("8.2");

    private final String versionString;

    PhpVersion(String versionString) {
        this.versionString = versionString;
    }

    public static Optional<PhpVersion> fromString(String version) {
        return Arrays.stream(values())
                .filter(v -> v.versionString.equals(version))
                .findFirst();
    }

    public static String supportedPhpVersions() {
        return Arrays.stream(values())
                .map(PhpVersion::getVersionString)
                .collect(Collectors.joining(", "));
    }

    public String getVersionString() {
        return versionString;
    }

    @Override
    public String toString() {
        return versionString;
    }
}
