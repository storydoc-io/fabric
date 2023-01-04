package io.storydoc.fabric.console.app.navigation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationRequest {
    private String environmentKey;
    private String systemComponentKey;
    private NavItem navItem;
}
