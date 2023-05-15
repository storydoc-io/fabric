package io.storydoc.fabric.query.app;

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
public class QueryDTO {

    private String environmentKey;
    private String systemComponentKey;
    private Map<String, String> attributes;
    private PagingDTO paging;
    private NavItem navItem;
}
