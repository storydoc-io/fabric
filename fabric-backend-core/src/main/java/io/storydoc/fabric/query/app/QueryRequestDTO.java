package io.storydoc.fabric.query.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequestDTO {

    private String environmentKey;
    private String systemComponentKey;
    private Map<String, String> attributes;

}
