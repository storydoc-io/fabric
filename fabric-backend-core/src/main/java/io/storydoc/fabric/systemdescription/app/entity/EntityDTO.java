package io.storydoc.fabric.systemdescription.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityDTO {

    private String name;
    private String entityType;
    private boolean json;
    private List<AttributeDTO> attributes;

}
