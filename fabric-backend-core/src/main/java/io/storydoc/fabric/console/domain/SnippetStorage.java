package io.storydoc.fabric.console.domain;

import io.storydoc.fabric.console.infra.Snippet;

import java.util.List;

public interface SnippetStorage {
    void addSnippet(String systemType, Snippet snippet);
    List<Snippet> getSnippets(String systemType);
    void updateSnippet(String systemType, Snippet build);
    void deleteSnippet(String systemType, String id);
}
