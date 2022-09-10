package io.storydoc.fabric.query.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryResponseItemDTO {
    private String systemType;
    private String queryResponseItemType;
    private Map<String, String> attributes;
    private List<QueryResponseItemDTO> children;

}
