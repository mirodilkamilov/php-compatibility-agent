package dev.mirodil;

import dev.mirodil.models.BreakingChange;
import dev.mirodil.serializers.BreakingChangesSerializer;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/optimus/IdeaProjects/php-compatibility-agent/src/main/resources/data/php_8_0-breaking-changes.json"; // adjust path if needed

        List<BreakingChange> changes = BreakingChangesSerializer.load(filePath);

        System.out.println("Loaded " + changes.size() + " breaking changes:\n");
        for (BreakingChange change : changes) {
            System.out.println("- " + change.getClass().getSimpleName() + ": " + change.getKey());
            System.out.println("  Description: " + change.getDescription());
            System.out.println("  Related: " + change.getRelated());
            System.out.println();
        }
    }
}