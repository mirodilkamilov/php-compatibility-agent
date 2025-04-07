package dev.mirodil;

import dev.mirodil.models.PhpVersion;
import dev.mirodil.models.migration_changes.BreakingChange;
import dev.mirodil.models.migration_changes.DeprecatedFeature;
import dev.mirodil.models.static_analyzer.AnalyzedCode;
import dev.mirodil.models.static_analyzer.StaticAnalyzer;
import dev.mirodil.serializers.BreakingChangesSerializer;
import dev.mirodil.serializers.DeprecatedFeaturesSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Agent {
    private final PhpVersion phpTargetVersion;

    public Agent(PhpVersion phpTargetVersion) {
        this.phpTargetVersion = phpTargetVersion;
    }

    public String analyze(File phpFile) throws IOException {
        List<BreakingChange> loadedBreakingChanges = BreakingChangesSerializer.load(phpTargetVersion);
        List<DeprecatedFeature> loadedDeprecatedFeatures = DeprecatedFeaturesSerializer.load(phpTargetVersion);

        StaticAnalyzer staticAnalyzer = new StaticAnalyzer();
        AnalyzedCode analyzedCode = staticAnalyzer.analyze(phpFile);

        // Call CompatibilityChecker checker = new CompatibilityChecker(phpTargetVersion, loadedBreakingChanges, loadedDeprecatedFeatures).analyze(analyzedCode)
        // Call String report = new MigrationAdvisor(checker); -- Prepare for user-friendly messages for final reporting
        return ""; // return report;
    }

    public PhpVersion getPhpTargetVersion() {
        return phpTargetVersion;
    }
}
