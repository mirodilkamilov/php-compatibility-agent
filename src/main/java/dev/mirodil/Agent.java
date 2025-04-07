package dev.mirodil;

import dev.mirodil.models.PhpVersion;

import java.io.File;

public class Agent {
    private final PhpVersion phpTargetVersion;

    public Agent(PhpVersion phpTargetVersion) {
        this.phpTargetVersion = phpTargetVersion;
    }

    public void analyze(File phpFile) {

    }

    public PhpVersion getPhpTargetVersion() {
        return phpTargetVersion;
    }
}
