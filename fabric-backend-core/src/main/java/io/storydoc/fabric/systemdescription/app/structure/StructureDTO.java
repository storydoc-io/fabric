package io.storydoc.fabric.systemdescription.app.structure;

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
public class StructureDTO {

    private String systemType;
    private String structureType;
    private String id;
    private Map<String, String> attributes;
    private List<StructureDTO> children;

}
