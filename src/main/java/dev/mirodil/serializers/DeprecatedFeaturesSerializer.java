package dev.mirodil.serializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirodil.models.DeprecatedFeature;
import dev.mirodil.models.DeprecatedFunction;
import dev.mirodil.models.IssueCategory;
import dev.mirodil.models.PhpVersion;
import dev.mirodil.utils.DataUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeprecatedFeaturesSerializer {

    public static List<DeprecatedFeature> load(String filePath) throws IOException {
        List<DeprecatedFeature> deprecatedFeatures = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            JsonNode root = mapper.readTree(file);
            PhpVersion phpTargetVersion = DataUtil.extractPhpVersionFromFileName(file.getName());

            deprecatedFeatures.addAll(parseDeprecatedFunctions(root.get("deprecated_functions"), phpTargetVersion));
            // TODO: parse removed_syntaxes and others
        } catch (IOException e) {
            System.err.println("Failed to load breaking changes: " + e.getMessage());
            throw e;
        }

        return deprecatedFeatures;
    }

    private static List<DeprecatedFunction> parseDeprecatedFunctions(JsonNode array, PhpVersion version) {
        List<DeprecatedFunction> list = new ArrayList<>();
        if (array != null && array.isArray()) {
            for (JsonNode node : array) {
                list.add(new DeprecatedFunction(
                        version,
                        IssueCategory.DEPRECATED_FUNCTION,
                        node.path("key").asText(),
                        node.path("description").asText(),
                        node.path("related").asText()
                ));
            }
        }
        return list;
    }

}