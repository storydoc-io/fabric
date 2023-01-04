package io.storydoc.fabric.elastic.metamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Schema {
    private String name;
    private Map<String, Object> source;
}
