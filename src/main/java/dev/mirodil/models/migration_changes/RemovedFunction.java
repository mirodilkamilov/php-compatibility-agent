package dev.mirodil.models.migration_changes;

import dev.mirodil.models.PhpVersion;
import dev.mirodil.models.static_analyzer.AnalyzedCode;

public class RemovedFunction extends BreakingChange {
    public RemovedFunction(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }

    @Override
    public boolean applies(AnalyzedCode analyzedCode) {
        return false;
    }
}
