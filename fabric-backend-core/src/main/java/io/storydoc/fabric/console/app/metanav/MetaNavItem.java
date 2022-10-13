package io.storydoc.fabric.console.app.metanav;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaNavItem {

    private String label;
    private String id;
    private Map<String, String> attributes;

}
