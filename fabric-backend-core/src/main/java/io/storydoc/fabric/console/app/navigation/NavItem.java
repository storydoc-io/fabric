package io.storydoc.fabric.console.app.navigation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavItem {

    private String label;
    private String type;
    private String id;
    private Map<String, String> attributes;

}
