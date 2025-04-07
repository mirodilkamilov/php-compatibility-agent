package dev.mirodil.models;

public class DeprecatedFunction extends DeprecatedFeature {
    public DeprecatedFunction(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }

    @Override
    public boolean applies(String analyzedCode) {
        return false;
    }
}
