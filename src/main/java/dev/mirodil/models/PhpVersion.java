package dev.mirodil.models;

import java.util.Arrays;
import java.util.Optional;

public enum PhpVersion {
    PHP_8_0("8.0"),
    PHP_8_1("8.1"),
    PHP_8_2("8.2");

    private final String versionString;

    PhpVersion(String versionString) {
        this.versionString = versionString;
    }

    public String getVersionString() {
        return versionString;
    }

    public static Optional<PhpVersion> fromString(String version) {
        return Arrays.stream(values())
                .filter(v -> v.versionString.equals(version))
                .findFirst();
    }

    @Override
    public String toString() {
        return versionString;
    }
}
