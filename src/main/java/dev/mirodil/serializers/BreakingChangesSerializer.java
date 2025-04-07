package dev.mirodil.serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirodil.models.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreakingChangesSerializer {

    public static List<BreakingChange> load(String filePath) throws IOException {
        List<BreakingChange> breakingChanges = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            JsonNode root = mapper.readTree(file);
            PhpVersion phpTargetVersion = extractPhpVersionFromFileName(file.getName());

            breakingChanges.addAll(parseReservedKeywords(root.get("reserved_keywords"), phpTargetVersion));
            breakingChanges.addAll(parseRemovedFunctions(root.get("removed_functions"), phpTargetVersion));
            breakingChanges.addAll(parseBehaviorChanges(root.get("behavior_changes"), phpTargetVersion));
            // TODO: parse removed_syntax and others
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

            String className = toPascalCase(key) + "Rule"; // e.g., "assertion_behaviour_change" -> "AssertionBehaviourChangeRule"
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

    private static PhpVersion extractPhpVersionFromFileName(String fileName) {
        String versionPattern = "php_(\\d+)_(\\d+)-"; // Regex to capture version numbers

        Pattern pattern = Pattern.compile(versionPattern);
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            return PhpVersion.fromString(major + "." + minor).orElseThrow();
        }

        throw new IllegalArgumentException("Cannot extract php version from file name: " + fileName);
    }

    private static String toPascalCase(String snakeCase) {
        String[] parts = snakeCase.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }
        return sb.toString();
    }

}