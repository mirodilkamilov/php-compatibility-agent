package dev.mirodil.models;

public abstract class BehaviourChange extends BreakingChange {
    public BehaviourChange(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }
}
