package dev.mirodil.models;

public class ReservedKeyword extends BreakingChange {
    public ReservedKeyword(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }

    @Override
    public boolean applies(String analyzedCode) {
        return false;
    }
}
