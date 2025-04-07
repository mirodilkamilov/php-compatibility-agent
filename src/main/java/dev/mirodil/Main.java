package dev.mirodil;

import dev.mirodil.models.BreakingChange;
import dev.mirodil.models.DeprecatedFeature;
import dev.mirodil.serializers.BreakingChangesSerializer;
import dev.mirodil.serializers.DeprecatedFeaturesSerializer;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/optimus/IdeaProjects/php-compatibility-agent/src/main/resources/data/php_8_0-breaking-changes.json";
        List<BreakingChange> breakingChanges = BreakingChangesSerializer.load(filePath);

        System.out.println("Loaded " + breakingChanges.size() + " breaking changes:\n");
        for (BreakingChange change : breakingChanges) {
            System.out.println("- " + change.getClass().getSimpleName() + ": " + change.getKey());
            System.out.println("  Description: " + change.getDescription());
            System.out.println("  Related: " + change.getRelated());
            System.out.println();
        }

        filePath = "/home/optimus/IdeaProjects/php-compatibility-agent/src/main/resources/data/php_8_0-deprecated-features.json";
        List<DeprecatedFeature> deprecatedFeatures = DeprecatedFeaturesSerializer.load(filePath);

        System.out.println("Loaded " + deprecatedFeatures.size() + " deprecated features:\n");
        for (DeprecatedFeature change : deprecatedFeatures) {
            System.out.println("- " + change.getClass().getSimpleName() + ": " + change.getKey());
            System.out.println("  Description: " + change.getDescription());
            System.out.println("  Related: " + change.getRelated());
            System.out.println();
        }
    }
}