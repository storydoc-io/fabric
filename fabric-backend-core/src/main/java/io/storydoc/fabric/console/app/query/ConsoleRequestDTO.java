package io.storydoc.fabric.console.app.query;

import io.storydoc.fabric.console.app.navigation.NavItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsoleRequestDTO {

    private String environmentKey;
    private String systemComponentKey;
    private Map<String, String> attributes;
    private NavItem navItem;
}
