package dev.mirodil.models.static_analyzer;

import java.util.List;

public class AnalyzedCode {
    List<Comment> comments;
    List<KeyWord> keyWords;
    List<Identifier> identifiers;
    List<BuiltInFunction> builtInFunctions;
    List<UserDefinedFunction> userDefinedFunctions;
}
