package dev.mirodil.serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirodil.models.*;
import dev.mirodil.utils.DataUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BreakingChangesSerializer {

    public static List<BreakingChange> load(String filePath) throws IOException {
        List<BreakingChange> breakingChanges = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            JsonNode root = mapper.readTree(file);
            PhpVersion phpTargetVersion = DataUtil.extractPhpVersionFromFileName(file.getName());

            breakingChanges.addAll(parseReservedKeywords(root.get("reserved_keywords"), phpTargetVersion));
            breakingChanges.addAll(parseRemovedFunctions(root.get("removed_functions"), phpTargetVersion));
            breakingChanges.addAll(parseBehaviorChanges(root.get("behavior_changes"), phpTargetVersion));
            // TODO: parse removed_syntaxes and others
        } catch (IOException e) {
            System.err.println("Failed to load breaking changes: " + e.getMessage());
            throw e;
        }

        return breakingChanges;
    }

    private static List<ReservedKeyword> parseReservedKeywords(JsonNode array, PhpVersion version) {
        List<ReservedKeyword> list = new ArrayList<>();
        if (array != null && array.isArray()) {
            for (JsonNode node : array) {
                list.add(new ReservedKeyword(
                        version,
                        IssueCategory.RESERVED_KEYWORD,
                        node.path("key").asText(),
                        node.path("description").asText(),
                        node.path("related").asText()
                ));
            }
        }
        return list;
    }

    private static List<RemovedFunction> parseRemovedFunctions(JsonNode array, PhpVersion version) {
        List<RemovedFunction> list = new ArrayList<>();
        if (array != null && array.isArray()) {
            for (JsonNode node : array) {
                list.add(new RemovedFunction(
                        version,
                        IssueCategory.REMOVED_FUNCTION,
                        node.path("key").asText(),
                        node.path("description").asText(),
                        node.path("related").asText()
                ));
            }
        }
        return list;
    }

    private static List<BehaviourChange> parseBehaviorChanges(JsonNode array, PhpVersion version) {
        List<BehaviourChange> list = new ArrayList<>();
        if (array == null || !array.isArray()) return list;

        for (JsonNode node : array) {
            String key = node.path("key").asText();
            String description = node.path("description").asText();
            String related = node.path("related").asText();

            String className = DataUtil.toPascalCase(key) + "Rule"; // e.g., "assertion_behaviour_change" -> "AssertionBehaviourChangeRule"
            String fullClassName = "dev.mirodil.models.rules." + className;

            try {
                Class<?> clazz = Class.forName(fullClassName);
                BehaviourChange instance = (BehaviourChange) clazz
                        .getConstructor(PhpVersion.class, IssueCategory.class, String.class, String.class, String.class)
                        .newInstance(version, IssueCategory.BEHAVIOR_CHANGE, key, description, related);

                list.add(instance);
            } catch (Exception e) {
                System.err.println("Could not load rule: " + fullClassName);
            }
        }

        return list;
    }
}