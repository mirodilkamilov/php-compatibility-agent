package dev.mirodil.models.rules;

import dev.mirodil.models.PhpVersion;
import dev.mirodil.models.migration_changes.BehaviourChange;
import dev.mirodil.models.migration_changes.IssueCategory;
import dev.mirodil.models.static_analyzer.AnalyzedCode;

public class IntroducedAttributesNoLongerCommentsRule extends BehaviourChange {
    public IntroducedAttributesNoLongerCommentsRule(PhpVersion phpTargetVersion, IssueCategory issueCategory, String key, String description, String related) {
        super(phpTargetVersion, issueCategory, key, description, related);
    }

    @Override
    public boolean applies(AnalyzedCode analyzedCode) {
        return false;
    }
}
