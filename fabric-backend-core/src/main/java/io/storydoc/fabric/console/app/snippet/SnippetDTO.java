package io.storydoc.fabric.console.app.snippet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnippetDTO {

    private String id;
    private String title;
    private Map<String, String> attributes;

}
