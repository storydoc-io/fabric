package io.storydoc.fabric.console.app.query;

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
public class ConsoleResponseDescriptionDTO {

    private String systemType;
    private String responseDescriptionType;
    private Map<String, String> attributes;
    private List<ConsoleResponseDescriptionDTO> children;

}
