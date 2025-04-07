package dev.mirodil.models.migration_changes;

import dev.mirodil.models.PhpVersion;
import dev.mirodil.models.static_analyzer.AnalyzedCode;

public abstract class MigrationChange {
    protected final PhpVersion phpTargetVersion;
    protected final IssueCategory issueCategory;
    protected final String key;
    protected final String description;
    protected final String related;

    public MigrationChange(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        this.phpTargetVersion = phpTargetVersion;
        this.issueCategory = issueCategory;
        this.key = key;
        this.description = description;
        this.related = related;
    }

    public abstract boolean applies(AnalyzedCode analyzedCode);

    public PhpVersion getPhpTargetVersion() {
        return phpTargetVersion;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public String getRelated() {
        return related;
    }
}
