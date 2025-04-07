package dev.mirodil.models;

public abstract class DeprecatedFeature extends MigrationChange {
    public DeprecatedFeature(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }
}
