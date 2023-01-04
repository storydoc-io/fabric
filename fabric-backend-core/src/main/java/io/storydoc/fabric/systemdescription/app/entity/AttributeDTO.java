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
public class AttributeDTO {

    private String name;
    private List<EntityDTO> entries;

}
