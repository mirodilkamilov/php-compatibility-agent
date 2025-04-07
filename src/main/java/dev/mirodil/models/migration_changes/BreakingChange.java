package dev.mirodil.models.migration_changes;

import dev.mirodil.models.PhpVersion;

public abstract class BreakingChange extends MigrationChange {
    public BreakingChange(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }
}
