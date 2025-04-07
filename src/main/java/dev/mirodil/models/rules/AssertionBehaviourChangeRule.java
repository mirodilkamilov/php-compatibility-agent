package dev.mirodil.models.rules;

import dev.mirodil.models.BehaviourChange;
import dev.mirodil.models.IssueCategory;
import dev.mirodil.models.PhpVersion;

public class AssertionBehaviourChangeRule extends BehaviourChange {
    public AssertionBehaviourChangeRule(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }

    @Override
    public boolean applies(String analyzedCode) {
        return false;
    }
}
